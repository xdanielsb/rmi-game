package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import metier.Player;
import metier.Vector2;

public interface IPlayerRemote extends Remote {
	public int registerPlayer(String p) throws RemoteException;
	public void Move(int id, double x, double y) throws RemoteException;
	public List<Vector2> UpdateAllPositions() throws RemoteException;

}
