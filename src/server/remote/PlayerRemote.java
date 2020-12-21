package server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.Board;
import interfaces.IPlayerRemote;
import interfaces.Player;
import server.control.GameManager;
import server.model.PlayerImpl;

public class PlayerRemote extends UnicastRemoteObject implements IPlayerRemote {

	private static final long serialVersionUID = 1L;

	private GameManager gameManager;
	private final Object mutex = new Object();
	private int playerIdIncrement = 0;

	public PlayerRemote(GameManager gameManager) throws RemoteException {
		this.gameManager = gameManager;
	}

	@Override
	public int registerPlayer(String p) throws RemoteException {
		synchronized (mutex) {
			Player newPlayer = new PlayerImpl(playerIdIncrement, p);
			gameManager.addPlayer(newPlayer);
			this.gameManager.getGUI().addLog("New player connected " + p + " with PID : " + playerIdIncrement);
			return playerIdIncrement++;
		}
	}

	@Override
	public void sendMousePosition(int id, float mouseX, float mouseY) throws RemoteException {
		gameManager.sendMousePosition(id, mouseX, mouseY);
	}

	@Override
	public void throwFood(int idPlayer, float mouseX, float mouseY) throws RemoteException {
		gameManager.throwFood(idPlayer, mouseX, mouseY);
	}

	@Override
	public void split(int idPlayer, float mouseX, float mouseY) throws RemoteException {
		gameManager.split(idPlayer, mouseX, mouseY);
	}
	
	@Override
	public Board getBoard() throws RemoteException {
		return gameManager.getBoard();
	}

	@Override
	public void removePlayer(int id) throws RemoteException {
		gameManager.removePlayer(id);
	}

	@Override
	public float getTimer() throws RemoteException {
		return gameManager.getTimer();
	}

	@Override
	public boolean gameOver() throws RemoteException {
		boolean isGameOver = gameManager.gameOver();
		if(isGameOver && this.gameManager.getBoard().getWinners() == null)
		{
			this.gameManager.getBoard().setWinner();
		}
		return isGameOver;
	}

}
