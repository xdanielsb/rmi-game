package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import metier.Player;
import metier.PlayerManager;
import metier.DataInfo;
import metier.GameManager;

public class PlayerRemoteImpl extends UnicastRemoteObject implements IPlayerRemote {
	private PlayerManager playerManager = new PlayerManager();
	private GameManager gameManager = new GameManager();
	public PlayerRemoteImpl() throws RemoteException
	{
		
	}

	@Override
	public int registerPlayer(String p) throws RemoteException {
		int pID = playerManager.getPlayerNumber();
		Player newPlayer = new Player(pID,0,p);
		playerManager.addPlayer(newPlayer);
		System.out.println("Ajout de : " + p);
		return pID;
		
	}

	@Override
	public void Move(int id, double x, double y) throws RemoteException {
		playerManager.Move(id, x, y);
		CheckFoodCollision(id);
	}

	@Override
	public List<DataInfo> UpdateAllPositions() throws RemoteException {
		List<DataInfo> res = new ArrayList<>();
		for(Player p:playerManager.listAllPlayers())
		{
			res.add(new DataInfo(p.getX(),p.getY(),p.getSize(),p.getTeamID()));
		}
		for(DataInfo di:gameManager.GetFoods())
		{
			res.add(di);
		}
		return res;
	}
	
	private void CheckFoodCollision(int id)
	{
		List<DataInfo> d = gameManager.GetFoods();
		System.out.println(d.size());
		/*Player p = playerManager.getPlayer(id);
		if(p == null)
		{
			System.out.println("Player ID unknown");
			return;
		}
		int size = p.getSize();
		
		for(DataInfo di:gameManager.GetFoods())
		{
			double dx = p.getX() - di.getX();
			double dy = p.getY() - di.getY();
			double length = Math.sqrt((dx*dx)+(dy*dy));
			if(length < size)
			{
				p.setSize(p.getSize()+di.getSize());
				gameManager.RemoveFood(di);
			}
			
		}*/
	}
	
}
