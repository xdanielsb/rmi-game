  package model;

import java.awt.Color;
import java.io.Serializable;

public abstract class CoordinateObject implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private double x;
	private double y;
	private double size;
	private Color color;
	private double inertiaX;
	private double inertiaY;

	public CoordinateObject(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.inertiaX = 0;
		this.inertiaY = 0;
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
		this.size = size;
	}
	
	public double getRadius() {
		return Math.sqrt(size/Math.PI);
	}
	
	public double dist(CoordinateObject coordObj) {
        return Math.hypot(coordObj.getX()-this.getX(), coordObj.getY()-this.getY());
    }
	
	public double getInertiaX() {
		return inertiaX;
	}
	
	public void setInertiaX(double inertiaX) {
		this.inertiaX = inertiaX;
	}

	public double getInertiaY() {
		return inertiaY;
	}
	
	public void setInertiaY(double inertiaY) {
		this.inertiaY = inertiaY;
	}
	
	public double getSpeedX() {
		return inertiaX;
	}

	public double getSpeedY() {
		return inertiaY;
	}
	
	public void applyMouvement() {
		x += getSpeedX();
		y += getSpeedY();
		inertiaX *= 0.75;
		if(inertiaX < 0.1) {
			inertiaX = 0;
		}
		inertiaY *= 0.75;
		if(inertiaY < 0.1) {
			inertiaY = 0;
		}
	}

}
