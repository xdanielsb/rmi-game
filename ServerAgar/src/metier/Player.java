package metier;

import java.io.Serializable;

public class Player implements Serializable {
	private final int id_player;
	private final int id_team;
	private final String name;
	private int size;
	private double posX, posY;
	
	public Player(int idP, int idT,String name)
	{
		this.name = name;
		id_player = idP;
		id_team = idT;
		if(idT == 0)
		{
			posX = 50;
			posY = 400;
		}
		else
		{
			posX = 750;
			posY = 400;
		}
		size = 50;
	}
	
	public void setSize(int s)
	{
		size = s;
	}
	public int getSize()
	{
		return size;
	}
	public int getPlayerID()
	{
		return id_player;
	}
	public int getTeamID()
	{
		return id_team;
	}
	public void setX(double x)
	{
		posX = x;
	}
	public double getX()
	{
		return posX;
	}
	public void setY(double y)
	{
		posY = y;
	}
	public double getY()
	{
		return posY;
	}
	public String getName()
	{
		return name;
	}
	

}
