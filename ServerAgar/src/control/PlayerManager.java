package control;

import java.util.List;

import model.Board;
import model.Player;

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

	public List<Player> getPlayersTeam(int team) {
		return board.getTeam(team).getPlayers();
	}

	public Player getPlayer(int id) {
		return board.getPlayer(id);
	}

	public void move(Player player, double x, double y) {
		if (player.isAlive()) {
			player.setX(x);
			player.setY(y);
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
				p.setSize(50);
				p.setAlive(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		new Thread(runnable).start();
	}
}
