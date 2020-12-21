package model;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.Remote;

public interface CoordinateObject extends Comparable<CoordinateObject>, Serializable, Remote {

	static final long serialVersionUID = 1L;

	public float getX();

	public float getY();

	public int getSize();

	public float getRadius();

	public Color getColor();

}
