package control;

import java.util.ArrayList;
import java.util.List;

import model.Food;
import model.Player;
import model.PlayerCell;
import model.Team;

public class PlayerManager {

	private Monitor monitor;

	public PlayerManager(Monitor monitor) {
		this.monitor = monitor;
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
			this.moveToward(player.getCell(), mouseX, mouseY);
		}
	}
	
	public List<Food> throwFood(Player player, float mouseX, float mouseY) {
		List<Food> foods = new ArrayList<>();
		for(PlayerCell cell : player.getCells()) {
			if(cell.getSize() >= PlayerCell.MIN_THROWING_FOOD_SIZE) {
				double distX = mouseX - cell.getX();
				double distY = mouseY - cell.getY();
				double dist = Math.hypot(distX, distY);
				
				Food food = new Food(cell, (float)(distX/dist), (float)(distY/dist));
				foods.add(food);
				
				cell.setSize(cell.getSize() - food.getSize());
				monitor.addScore(cell.getPlayer().getTeam(), -food.getSize());
			}
		}
		return foods;
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
				p.addCell(new PlayerCell(p, PlayerCell.CELL_MIN_SIZE));
				p.setAlive(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		new Thread(runnable).start();
	}
}
