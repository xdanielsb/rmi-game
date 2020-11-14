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
	float gameTimer = 180;

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
		for (SpaceObject di : gameManager.getFoods()) {
			res.add(di);
		}
		return res;
	}

	private void checkPlayerCollision() {
		List<Player> team1, team2;
		team1 = playerManager.getPlayersTeam(0);
		team2 = playerManager.getPlayersTeam(1);
		for( Player pteam1: team1) {
			if(!pteam1.isAlive()) continue;
			for(Player pteam2: team2) {
				if(!pteam2.isAlive()) continue;
				if( (pteam1.dist(pteam2) < Math.max(pteam1.getSize()/4, pteam2.getSize()/4)) && Math.abs(pteam1.getSize()-pteam2.getSize()) >= 10) {
					if (pteam1.getSize() < pteam2.getSize()) {
						//pteam1.setAlive(false);
						pteam2.setSize(Math.sqrt((pteam2.getSize() / 2) * (pteam2.getSize() / 2)
								+ (pteam1.getSize() / 2) * (pteam1.getSize() / 2)) * 2);
						updateScore(pteam2, (int) pteam2.getSize());
						playerManager.removePlayer(pteam1);
						break;
					}else {
						//pteam2.setAlive(false);
						pteam1.setSize(Math.sqrt((pteam2.getSize() / 2) * (pteam2.getSize() / 2)
								+ (pteam1.getSize() / 2) * (pteam1.getSize() / 2)) * 2);
						updateScore(pteam1, (int) pteam1.getSize());
						playerManager.removePlayer(pteam2);
					}
				}
			}
		}
	}


	private void updateScore(Player p, int amount) {
		playerManager.addScore(p.getTeam(), amount);
	}

	private void checkFoodCollision() {
		List<SpaceObject> eatenFood = new ArrayList<>();
		for (Player p : playerManager.getPlayers()) {
			double size = p.getSize() / 2;
			for (SpaceObject di : gameManager.getFoods()) {
				double dx = p.getX() - di.getX();
				double dy = p.getY() - di.getY();
				double length = Math.sqrt((dx * dx) + (dy * dy));
				if (length < size) {
					p.setSize(Math.sqrt((p.getSize() / 2) * (p.getSize() / 2) + (di.getSize() / 2) * (di.getSize() / 2))
							* 2);
					updateScore(p, (int) di.getSize());
					eatenFood.add(di);
					//di.setAlive(false);

				}
			}
			gameManager.removeFood(eatenFood);
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

	@Override
	public void erasePlayer(int id) throws RemoteException {
		playerManager.erasePlayer(id);
	}

}
