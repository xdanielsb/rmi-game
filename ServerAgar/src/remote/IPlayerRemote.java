package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.DataInfo;
import model.Player;

public interface IPlayerRemote extends Remote {
	public int registerPlayer(String p) throws RemoteException;

	public void move(int id, double x, double y) throws RemoteException;

	public List<DataInfo> updateAllPositions(int ID) throws RemoteException;

	public float getTimer() throws RemoteException;

	public Player getPlayer(int ID) throws RemoteException;

	public int getScore(int teamID) throws RemoteException;

}
