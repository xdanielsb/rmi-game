package model;

import java.awt.Color;
import java.io.Serializable;

public class SpaceObject implements Serializable {
	private double x;
	private double y;
	private boolean isAlive;
	private double size;
	private Color color;
	private int id;

	public SpaceObject() {

	}

	public SpaceObject(double x, double y, double size) {
		super();
		this.x = x;
		this.y = y;
		this.size = size;
		this.isAlive = true;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setLife() {
		isAlive = !isAlive;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = Math.min(750, size);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public double dist(SpaceObject p) {
		return Math.hypot(p.getX() - this.getX(), p.getY() - this.getY());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
