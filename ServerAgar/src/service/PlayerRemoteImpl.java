package service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import metier.Player;
import metier.PlayerManager;
import metier.DataInfo;
import metier.GameManager;

public class PlayerRemoteImpl extends UnicastRemoteObject implements IPlayerRemote, ActionListener {
	private PlayerManager playerManager = new PlayerManager();
	private GameManager gameManager = new GameManager();
	Timer tm = new Timer(25, this);
	float gameTimer = 100;

	public PlayerRemoteImpl() throws RemoteException {
		tm.start();
	}

	@Override
	public int registerPlayer(String p) throws RemoteException {
		int pID = playerManager.getPlayerNumber();
		int idTeam = playerManager.getTeamOne() <= playerManager.getTeamtwo() ? 0 : 1;
		Player newPlayer = new Player(pID, idTeam, p);
		playerManager.addPlayer(newPlayer);
		System.out.println("Ajout de : " + p);
		return pID;

	}

	@Override
	public void Move(int id, double x, double y) throws RemoteException {
		playerManager.Move(id, x, y);
	}

	@Override
	public List<DataInfo> UpdateAllPositions(int ID) throws RemoteException {
		List<DataInfo> res = new ArrayList<>();
		for (Player p : playerManager.listAllPlayers()) {
			if (p.getPlayerID() != ID)
				res.add(new DataInfo(p.getX(), p.getY(), p.getSize(), p.getTeamID()));
		}
		for (DataInfo di : gameManager.GetFoods()) {
			res.add(di);
		}
		return res;
	}

	private void CheckPlayerCollision() {
		List<Player> players = playerManager.listAllPlayers();
		for (Player p : players) {
			int size = p.getSize() / 4;
			for (Player other : players) {
				if (other.getPlayerID() != p.getPlayerID()) {
					if (other.getTeamID() != p.getTeamID() && Math.abs(other.getSize() - p.getSize()) > 10) {
						double dx = other.getX() - p.getX();
						double dy = other.getY() - p.getY();
						double length = Math.sqrt((dx * dx) + (dy * dy));
						if (length < size) {
							boolean pBigger = p.getSize() > other.getSize();
							if (pBigger) {
								p.setSize(p.getSize() + other.getSize());
								UpdateScore(p, other.getSize());
							} else {
								other.setSize(other.getSize() + p.getSize());
								UpdateScore(other, p.getSize());
							}

							playerManager.RemovePlayer(pBigger ? other : p);
							CheckPlayerCollision();
							return;
						}
					}
				}
			}
		}
	}

	private void UpdateScore(Player p, int amount) {
		playerManager.addScore(p.getTeamID(), amount);
	}

	private void CheckFoodCollision(/* int id */) {
		List<Player> players = playerManager.listAllPlayers();
		List<DataInfo> eatenFood = new ArrayList<>();
		for (Player p : players) {
			int size = p.getSize() / 2;
			for (DataInfo di : gameManager.GetFoods()) {
				double dx = p.getX() - di.getX();
				double dy = p.getY() - di.getY();
				double length = Math.sqrt((dx * dx) + (dy * dy));
				if (length < size) {
					p.setSize(p.getSize() + di.getSize());
					UpdateScore(p, di.getSize());
					eatenFood.add(di);
				}
			}
			gameManager.RemoveFood(eatenFood);
			eatenFood.clear();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		CheckFoodCollision();
		CheckPlayerCollision();
		gameTimer -= 0.025;
		// Reset timer for next Tick
		tm.start();
	}

	@Override
	public float getTimer() throws RemoteException {
		// TODO Auto-generated method stub
		return gameTimer;
	}

	@Override
	public Player getPlayer(int ID) throws RemoteException {
		return playerManager.getPlayer(ID);
	}

	@Override
	public int getScore(int teamID) throws RemoteException {
		// TODO Auto-generated method stub
		return playerManager.getScore(teamID);
	}

}
