package metier;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
	private List<Player> players;
	private int nbTone;
	private int nbTtwo;
	
	public PlayerManager()
	{
		players = new ArrayList<>();
		nbTone = 0;
		nbTtwo = 0;
	}
	
	public int getTeamOne()
	{
		return nbTone;
	}
	public int getTeamtwo()
	{
		return nbTtwo;
	}
	
	public void addPlayer(Player p)
	{
		players.add(p);
		if(p.getTeamID()==0)
			nbTone++;
		else
			nbTtwo++;
	}
	
	public List<Player> listAllPlayers()
	{
		return players;
	}
	
	public int getPlayerNumber()
	{
		return players.size();
	}
	
	public Player getPlayer(int id)
	{
		Player res = null;
		for(Player p:players)
		{
			if(p.getPlayerID() == id)
			{
				res = p;
				break;
			}
		}
		return res;
	}
	
	public void Move(int id, double x, double y)
	{
		for(Player p:players)
		{
			if(p.getPlayerID() == id)
			{
				p.setX(x);
				p.setY(y);
				return;
			}
		}
	}
	
	public void RemovePlayer(Player p)
	{
		if(p.getTeamID() == 0)
			nbTone --;
		else
			nbTtwo --;
		players.remove(p);
	}
}
