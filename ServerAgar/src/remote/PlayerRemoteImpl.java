package remote;

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
import model.DataInfo;
import model.Player;

public class PlayerRemoteImpl extends UnicastRemoteObject implements IPlayerRemote, ActionListener {
	private PlayerManager playerManager = new PlayerManager();
	private GameManager gameManager;
	private final Object mutex = new Object();
	Timer tm = new Timer(25, this);
	float gameTimer = 60;

	public PlayerRemoteImpl(GameManager gameManager) throws RemoteException {
		this.gameManager = gameManager;
		tm.start();
	}

	@Override
	public int registerPlayer(String p) throws RemoteException {
		synchronized(mutex)
		{
			int pID = playerManager.getPlayerNumber();
			int idTeam = playerManager.getTeamOne() <= playerManager.getTeamtwo() ? 0 : 1;
			Player newPlayer = new Player(pID, idTeam, p);
			playerManager.addPlayer(newPlayer);
			System.out.println("Ajout de : " + p);
			this.gameManager.getGUI().addLog("New player connected " + p + " with PID : " + pID );
			return pID;
		}
	}

	@Override
	public void move(int id, double x, double y) throws RemoteException {
		playerManager.move(id, x, y);
	}

	@Override
	public List<DataInfo> updateAllPositions(int ID) throws RemoteException {
		List<DataInfo> res = new ArrayList<>();
		for (Player p : playerManager.getPlayers()) {
			if (p.getPlayerID() != ID)
				res.add(new DataInfo(p.getX(), p.getY(), p.getSize(), p.getTeamID()));
		}
		for (DataInfo di : gameManager.GetFoods()) {
			res.add(di);
		}
		return res;
	}

	private void CheckPlayerCollision() {
		Collection<Player> players = playerManager.getPlayers();
		for (Player p : players) {
			if(!p.isAlive())
				continue;
			double size = p.getSize() / 4;
			for (Player other : players) {
				if(!other.isAlive())
					continue;
				if (other.getPlayerID() != p.getPlayerID()) {
					if (other.getTeamID() != p.getTeamID() && Math.abs(other.getSize() - p.getSize()) > 10) {
						double dx = other.getX() - p.getX();
						double dy = other.getY() - p.getY();
						double length = Math.sqrt((dx * dx) + (dy * dy));
						if (length < size) {
							boolean pBigger = p.getSize() > other.getSize();
							if (pBigger) {
								//p.setSize(p.getSize() + other.getSize());
								p.setSize(Math.sqrt((p.getSize()/2) * (p.getSize()/2) + (other.getSize()/2) * (other.getSize()/2))*2);
								UpdateScore(p, (int)other.getSize());
							} else {
								other.setSize(Math.sqrt((p.getSize()/2) * (p.getSize()/2) + (other.getSize()/2) * (other.getSize()/2))*2);
								UpdateScore(other, (int)p.getSize());
							}

							playerManager.removePlayer(pBigger ? other : p);
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
		List<DataInfo> eatenFood = new ArrayList<>();
		for (Player p : playerManager.getPlayers()) {
			double size = p.getSize() / 2;
			for (DataInfo di : gameManager.GetFoods()) {
				double dx = p.getX() - di.getX();
				double dy = p.getY() - di.getY();
				double length = Math.sqrt((dx * dx) + (dy * dy));
				if (length < size) {
					p.setSize(Math.sqrt((p.getSize()/2)*(p.getSize()/2) + (di.getSize()/2)*(di.getSize()/2))*2);
					UpdateScore(p, (int)di.getSize());
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
		return gameTimer <= 0? 0:gameTimer;
	}
	
	@Override
	public boolean gameOver(){
		System.out.println(gameTimer);
		return gameTimer <= 0;
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
