package metier;

import java.io.Serializable;

public class DataInfo implements Serializable {
	private double xPos, yPos;
	private double size;
	private int team;
	private double R,G,B;

	public DataInfo(double x, double y, double s, int t) {
		xPos = x;
		yPos = y;
		size = s;
		team = t;
	}
	public DataInfo(double x, double y, double s, int t,double R,double G,double B) {
		xPos = x;
		yPos = y;
		size = s;
		team = t;
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public double getR()
	{
		return R;
	}
	public double getG()
	{
		return G;
	}
	public double getB()
	{
		return B;
	}
	

	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public double getSize() {
		return size;
	}

	public int getTeam() {
		return team;
	}

}
