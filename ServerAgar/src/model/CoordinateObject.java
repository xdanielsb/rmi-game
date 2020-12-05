package model;

import java.awt.Color;
import java.io.Serializable;

public abstract class CoordinateObject implements Serializable {
	
	private double x;
	private double y;
	private double size;
	private Color color;

	public CoordinateObject(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
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
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = Math.min(750, size);
	}
	
	public double dist(CoordinateObject coordObj) {
        return Math.hypot(coordObj.getX()-this.getX(), coordObj.getY()-this.getY());
    }

}
