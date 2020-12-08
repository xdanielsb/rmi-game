package model;

import java.awt.Color;

public class FeedableObject extends CoordinateObject{

	private static final long serialVersionUID = 1L;
	
	public FeedableObject(double x, double y, int size, Color color) {
		super(x, y, size, color);
	}
	
	public void eat(CoordinateObject coordObj) {
		setSize(getSize() + coordObj.getSize());
	}
	
}
