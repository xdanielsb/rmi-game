package server.control;

import java.util.ArrayList;
import java.util.List;

import interfaces.CoordinateObject;
import interfaces.FeedableObject;
import interfaces.Food;
import interfaces.Player;
import interfaces.PlayerCell;
import interfaces.SpikeCell;
import interfaces.Team;
import server.model.FoodImpl;
import server.model.PlayerCellImpl;
import server.model.SpikeCellImpl;

public class PlayerManager {

	private Monitor monitor;

	private List<PlayerCell> cellsToAdd;

	public PlayerManager(Monitor monitor) {
		this.monitor = monitor;
		cellsToAdd = new ArrayList<>();
	}

	public void addScore(Team team, int amount) {
		// non bloquant pour l'appelant
		Runnable runnable = () -> {
			monitor.addScore(team, amount);
		};
		new Thread(runnable).start();

	}

	public void sendMousePosition(Player player, float mouseX, float mouseY) {
		if (player.isAlive()) {
			for(PlayerCell cell : player.getCells()) {
				moveToward(cell, mouseX, mouseY);
			}
		}
	}

	public List<Food> throwFood(Player player, float mouseX, float mouseY) {
		List<Food> foods = new ArrayList<>();
		for(PlayerCell cell : player.getCells()) {
			if(cell.getSize() >= PlayerCell.MIN_THROWING_FOOD_SIZE) {
				double distX = mouseX - cell.getX();
				double distY = mouseY - cell.getY();
				double dist = Math.hypot(distX, distY);

				Food food = new FoodImpl(cell, (float)(distX/dist), (float)(distY/dist));
				foods.add(food);

				cell.setSize(cell.getSize() - food.getSize());
				monitor.addScore(cell.getPlayer().getTeam(), -food.getSize());
			}
		}
		return foods;
	}

	public void split(Player player, float mouseX, float mouseY) {
		for(PlayerCell cell : player.getCells()) {
			if(cell.getSize() >= PlayerCell.MIN_SPLITTING_SIZE) {
				double distX = mouseX - cell.getX();
				double distY = mouseY - cell.getY();
				double dist = Math.hypot(distX, distY);
				PlayerCell newCell = new PlayerCellImpl(cell, cell.getSize()/2, (float)(distX/dist), (float)(distY/dist));
				cellsToAdd.add(newCell);
			}
		}
	}

	public void playerCellExplosion(PlayerCell cell) {
		float ratio = SpikeCell.EXPLOSION_INITIAL_RATIO;
		int initialSize = cell.getSize();

		for(int i = 0; i < SpikeCell.EXPLOSION_NUMBER; i++) {
			if(cell.getSize() < PlayerCell.MIN_SPLITTING_SIZE) {
				break;
			}
			int size = Math.max((int)(initialSize*ratio), PlayerCell.CELL_MIN_SIZE);
			double angle = Math.random()*Math.toRadians(360);
			cell.getPlayer().addCell(new PlayerCellImpl(cell, size, (float)Math.cos(angle), (float)Math.sin(angle)));
			ratio /= 2;
		}

	}

	public void addWaitingPlayerCells() {
		for(PlayerCell cell : cellsToAdd) {
			cell.getPlayer().addCell(cell);
		}
		cellsToAdd.clear();
	}

	public void moveToward(PlayerCell cell, float mouseX, float mouseY) {
		if(cell != null) {
			float distX = mouseX - cell.getX();
			float distY = mouseY - cell.getY();
			float dist = (float)Math.hypot(distX, distY);
			if(dist > 10) {
				cell.setMovementX(distX/dist);
				cell.setMovementY(distY/dist);
			}else {
				cell.setMovementX(0);
				cell.setMovementY(0);
			}
		}
	}

	public void removePlayer(Player p) {
		p.setAlive(false);
		Runnable runnable = () -> {
			try {
				Thread.sleep(3000);
				p.addCell(new PlayerCellImpl(p, PlayerCell.CELL_MIN_SIZE));
				p.setAlive(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		new Thread(runnable).start();
	}

	public void tryEat(FeedableObject eater, CoordinateObject eated, GameManager manager) {
		if(eater instanceof PlayerCell) {
			PlayerCell cell = (PlayerCell) eater;
			if(eated instanceof Food) {
				playerTryToEatFood(cell, (Food)eated, manager);
			} else if(eated instanceof PlayerCell) {
				playerTryToEatPlayer(cell, (PlayerCell)eated, manager);
			} else if(eated instanceof SpikeCell) {
				playerTryToEatSpike(cell, (SpikeCell)eated, manager);
			}
		} else if(eater instanceof SpikeCell) {
			if(eated instanceof Food) {
				spikeTryToEatFood((SpikeCell)eater, (Food)eated, manager);
			}
		}
	}

	public void playerTryToEatFood(
			PlayerCell eater,
			Food eated,
			GameManager manager
			) {
		eater.eat(eated);
		addScore(eater.getPlayer().getTeam(), eated.getSize());
		manager.removeFood(eated);
	}

	public void playerTryToEatPlayer(
			PlayerCell eater,
			PlayerCell eated,
			GameManager manager	) {
		if(eater.getPlayer() == eated.getPlayer() || eated.getSize() < eater.getSize()*0.98) {
			eater.eat(eated);
			addScore(eater.getPlayer().getTeam(), eated.getSize());
			manager.removePlayerCell(eated);
		}
	}

	public void playerTryToEatSpike(
			PlayerCell eater,
			SpikeCell eated,
			GameManager manager
	) {
		if(eated.getSize() < eater.getSize()*0.98) {
			playerCellExplosion(eater);
			manager.removeSpikeCell(eated);
		}
	}

	public void spikeTryToEatFood(
			SpikeCell eater,
			Food eated,
			GameManager manager
	) {
		eater.eat(eated);
		if(eater.getSize() > SpikeCell.MAX_SPIKE_SIZE) {
			double dist = Math.hypot(eated.getInertiaX(), eated.getInertiaY());
			manager.addSpike(new SpikeCellImpl(
					eater,
					eater.getSize()/2,
					(float)(eated.getInertiaX()/dist),
					(float)(eated.getInertiaY()/dist)
			));
		}
		manager.removeFood(eated);
	}
}
