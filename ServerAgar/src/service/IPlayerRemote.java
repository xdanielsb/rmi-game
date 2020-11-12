package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import control.DataInfo;
import control.Player;

public interface IPlayerRemote extends Remote {
	public int registerPlayer(String p) throws RemoteException;

	public void Move(int id, double x, double y) throws RemoteException;

	public List<DataInfo> UpdateAllPositions(int ID) throws RemoteException;

	public float getTimer() throws RemoteException;

	public Player getPlayer(int ID) throws RemoteException;

	public int getScore(int teamID) throws RemoteException;

}
