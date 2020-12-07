package displayer;

import java.awt.Color;

import model.CoordinateObject;
import processing.core.PApplet;

public class FoodDisplayer {

	public static void draw(CoordinateObject coordinateObject, PApplet sketch)
	{
		Color foodColor = coordinateObject.getColor();
		sketch.fill(foodColor.getRed(), foodColor.getGreen(), foodColor.getBlue());
		sketch.circle((float) (coordinateObject.getX()), (float) (coordinateObject.getY()), (float) (3));
	}
}
