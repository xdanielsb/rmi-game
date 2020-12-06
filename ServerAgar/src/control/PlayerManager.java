package control;

import java.util.List;

import model.Board;
import model.Player;
import model.PlayerCell;
import model.Team;

public class PlayerManager {

	private Monitor monitor;
	private Board board;

	public PlayerManager(Board board) {
		monitor = new Monitor(board);
		this.board = board;
	}
	
	public void addScore(int teamID, int amount) {
		// non bloquant pour l'appelant
		Runnable runnable = () -> {
			monitor.addScore(teamID, amount);
		};

		new Thread(runnable).start();

	}

	public void addPlayer(Player p) {
		board.addPlayer(p);
	}

	public void erasePlayer(int id) {
		board.removePlayer(id);
	}

	public List<Player> getPlayersTeam(Team team) {
		return team.getPlayers();
	}

	public Player getPlayer(int id) {
		return board.getPlayer(id);
	}

	public void sendMousePosition(Player player, double mouseX, double mouseY) {
		if (player.isAlive()) {
			this.moveToward(player.getCell(), mouseX, mouseY);
		}
	}
	
	public void moveToward(PlayerCell cell, double mouseX, double mouseY) {
		double distX = mouseX - cell.getX();
		double distY = mouseY - cell.getY();
		double dist = Math.hypot(distX, distY);
		if(dist > 10) {
			cell.moveTo(distX/dist, distY/dist);
		}
	}

	private void resetPosition(Player p) {
		p.setX(p.getTeam().getSpawnX());
		p.setY(p.getTeam().getSpawnY());
	}

	public void removePlayer(Player p) {
		p.setAlive(false);
		p.setSize(0);
		Runnable runnable = () -> {
			try {
				Thread.sleep(3000);
				resetPosition(p);
				p.setSize(PlayerCell.CELL_MIN_SIZE);
				p.setAlive(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		new Thread(runnable).start();
	}
}
