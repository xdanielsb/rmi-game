package control;

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
	
	public void sendMousePosition(Player player, double mouseX, double mouseY) {
		if (player.isAlive()) {
			this.moveToward(player.getCell(), mouseX, mouseY);
		}
	}
	
	public void moveToward(PlayerCell cell, double mouseX, double mouseY) {
		if(cell != null) {			
			double distX = mouseX - cell.getX();
			double distY = mouseY - cell.getY();
			double dist = Math.hypot(distX, distY);
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
				p.add(new PlayerCell(p, PlayerCell.CELL_MIN_SIZE));
				p.setAlive(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		new Thread(runnable).start();
	}
}
