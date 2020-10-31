package metier;

import java.io.Serializable;

public class Vector2 implements Serializable {
	private double xPos, yPos;
	
	public Vector2(double x, double y)
	{
		xPos = x;
		yPos = y;
	}
	
	public double getX()
	{
		return xPos;
	}
	public double getY()
	{
		return yPos;
	}
	
}
