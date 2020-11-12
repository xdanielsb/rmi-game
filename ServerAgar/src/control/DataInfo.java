package control;

import java.awt.Color;
import java.io.Serializable;

public class DataInfo implements Serializable {
	private double xPos, yPos;
	private double size;
	private int team;
	private Color color;

	public DataInfo(double x, double y, double s, int t) {
		xPos = x;
		yPos = y;
		size = s;
		team = t;
		this.color = new Color((int)(Math.random() * 0x1000000)).brighter();
	}


	public Color getColor() {
		return this.color;
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
