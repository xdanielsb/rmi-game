package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Board;

public interface IPlayerRemote extends Remote {
	
	public int registerPlayer(String p) throws RemoteException;

	public void sendMousePosition(int id, float x, float y) throws RemoteException;

	public float getTimer() throws RemoteException;

	public Board getBoard() throws RemoteException;
	
	public boolean gameOver() throws RemoteException;
	
	public void removePlayer(int id) throws RemoteException;

}
