package remote;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.Timer;

import control.GameManager;
import control.PlayerManager;
import model.Player;
import model.SpaceObject;

public class PlayerRemoteImpl extends UnicastRemoteObject implements IPlayerRemote, ActionListener {
	private PlayerManager playerManager = new PlayerManager();
	private GameManager gameManager;
	private final Object mutex = new Object();
	Timer tm = new Timer(25, this);
	float gameTimer = 600;

	public PlayerRemoteImpl(GameManager gameManager) throws RemoteException {
		this.gameManager = gameManager;
		tm.start();
	}

	@Override
	public int registerPlayer(String p) throws RemoteException {
		synchronized (mutex) {
			int pID = playerManager.getPlayerNumber();
			int idTeam = playerManager.getTeamOne() <= playerManager.getTeamtwo() ? 0 : 1;
			Player newPlayer = new Player(pID, idTeam, p);
			if (idTeam == 0)
				newPlayer.setColor(new Color(255, 0, 0));
			else if (idTeam == 1)
				newPlayer.setColor(new Color(0, 0, 255));
			playerManager.addPlayer(newPlayer);
			this.gameManager.getGUI().addLog("New player connected " + p + " with PID : " + pID);
			return pID;
		}
	}

	@Override
	public void move(int id, double x, double y) throws RemoteException {
		playerManager.move(id, x, y);
	}

	@Override
	public List<SpaceObject> updateAllPositions(int ID) throws RemoteException {
		List<SpaceObject> res = new ArrayList<>();
		for (Player p : playerManager.getPlayers()) {
			if (p.getId() != ID)
				res.add(p);
		}
		for (SpaceObject di : gameManager.GetFoods()) {
			res.add(di);
		}
		return res;
	}

	private void checkPlayerCollision() {
		Collection<Player> players = playerManager.getPlayers();
		for (Player p : players) {
			if (!p.isAlive())
				continue;
			double size = p.getSize() / 4;
			for (Player other : players) {
				if (!other.isAlive())
					continue;
				if (other.getId() != p.getId()) {
					if (other.getTeam() != p.getTeam() && Math.abs(other.getSize() - p.getSize()) > 10) {
						double dx = other.getX() - p.getX();
						double dy = other.getY() - p.getY();
						double length = Math.sqrt((dx * dx) + (dy * dy));
						if (length < size) {
							boolean pBigger = p.getSize() > other.getSize();
							if (pBigger) {
								// p.setSize(p.getSize() + other.getSize());
								p.setSize(Math.sqrt((p.getSize() / 2) * (p.getSize() / 2)
										+ (other.getSize() / 2) * (other.getSize() / 2)) * 2);
								updateScore(p, (int) other.getSize());
							} else {
								other.setSize(Math.sqrt((p.getSize() / 2) * (p.getSize() / 2)
										+ (other.getSize() / 2) * (other.getSize() / 2)) * 2);
								updateScore(other, (int) p.getSize());
							}

							playerManager.removePlayer(pBigger ? other : p);
							checkPlayerCollision();
							return;
						}
					}
				}
			}
		}
	}

	private void updateScore(Player p, int amount) {
		playerManager.addScore(p.getTeam(), amount);
	}

	private void checkFoodCollision(/* int id */) {
		List<Integer> eatenFood = new ArrayList<>();
		for (Player p : playerManager.getPlayers()) {
			double size = p.getSize() / 2;
			for (SpaceObject di : gameManager.GetFoods()) {
				double dx = p.getX() - di.getX();
				double dy = p.getY() - di.getY();
				double length = Math.sqrt((dx * dx) + (dy * dy));
				if (length < size) {
					p.setSize(Math.sqrt((p.getSize() / 2) * (p.getSize() / 2) + (di.getSize() / 2) * (di.getSize() / 2))
							* 2);
					updateScore(p, (int) di.getSize());
					eatenFood.add(di.getId());
				}
			}
			gameManager.RemoveFood(eatenFood);
			eatenFood.clear();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		checkFoodCollision();
		checkPlayerCollision();
		gameTimer -= 0.025;
		// Reset timer for next Tick
		tm.start();
	}

	@Override
	public float getTimer() throws RemoteException {
		return gameTimer <= 0 ? 0 : gameTimer;
	}

	@Override
	public boolean gameOver() {
		return gameTimer <= 0;
	}

	@Override
	public Player getPlayer(int ID) throws RemoteException {
		return playerManager.getPlayer(ID);
	}

	@Override
	public int getScore(int teamID) throws RemoteException {
		return playerManager.getScore(teamID);
	}

}
