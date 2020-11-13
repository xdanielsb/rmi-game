package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Player;

public class PlayerManager {
	// private List<Player> players;
	private Map<Integer, Player> players;
	Monitor monitor;
	private int nbTone;
	private int nbTtwo;
	private int scoreTone;
	private int scoreTtwo;

	public PlayerManager() {
		monitor = new Monitor();
		players = new HashMap<Integer, Player>();
		nbTone = 0;
		nbTtwo = 0;
		scoreTone = 0;
		scoreTtwo = 0;
	}

	public int getTeamOne() {
		return nbTone;
	}

	public int getTeamtwo() {
		return nbTtwo;
	}

	public int getScore(int teamID) {
		return teamID == 0 ? monitor.getScoreOne() : monitor.getScoreTwo();
	}

	public void addScore(int teamID, int amount) {
		// non bloquant pour l'appelant
		Runnable runnable = () -> {
			if (teamID == 0)
				monitor.addScoreOne(amount);
			else
				monitor.addScoreTwo(amount);
		};

		new Thread(runnable).start();

	}

	public void addPlayer(Player p) {
		players.put(p.getPlayerID(), p);
		if (p.getTeamID() == 0) {
			nbTone++;
		} else {
			nbTtwo++;
		}
	}

	public Collection<Player> getPlayers() {
		return players.values();
	}

	public int getPlayerNumber() {
		return players.size();
	}

	public Player getPlayer(int id) {
		return players.get(id);
	}

	public void Move(int id, double x, double y) {
		Player p = players.get(id);
		if (p.getPlayerID() == id && p.isAlive()) {
			p.setX(x);
			p.setY(y);
		}
	}

	private void ResetPosition(Player p) {
		p.setX(p.getTeamID() == 0 ? 50 : 750);
		p.setY(400);
	}

	public void RemovePlayer(Player p) {
		/*
		 * if (p.getTeamID() == 0) nbTone--; else nbTtwo--; players.remove(p);
		 */
		// Move(p.getPlayerID(), p.getTeamID()==0? 50:750, 400);
		p.setLife();
		p.setSize(0);
		Runnable runnable = () -> {
			try {
				Thread.sleep(3000);
				// Move(p.getPlayerID(), p.getTeamID()==0? 50:750, 400);
				ResetPosition(p);
				p.setSize(50);
				p.setLife();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		new Thread(runnable).start();
	}
}
