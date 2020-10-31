package metier;

import java.io.Serializable;

public class DataInfo implements Serializable {
	private double xPos, yPos;
	private int size,team;
	
	public DataInfo(double x, double y, int s, int t)
	{
		xPos = x;
		yPos = y;
		size = s;
		team = t;
	}
	
	public double getX()
	{
		return xPos;
	}
	public double getY()
	{
		return yPos;
	}
	public int getSize()
	{
		return size;
	}
	public int getTeam()
	{
		return team;
	}
	
}
