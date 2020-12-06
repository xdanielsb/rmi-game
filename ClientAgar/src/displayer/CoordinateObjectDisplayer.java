package displayer;

import java.awt.Color;

import model.CoordinateObject;
import model.Food;
import model.Player;
import processing.core.PApplet;
import view.MapGraphics;

public class CoordinateObjectDisplayer {

	public static void draw(CoordinateObject coordinateObject, Player p, float zoomRatio, PApplet sketch)
	{
		Color foodColor = coordinateObject.getColor();
		sketch.fill(foodColor.getRed(), foodColor.getGreen(), foodColor.getBlue());
		double objX = coordinateObject.getX();
		double objY = coordinateObject.getY();
		double offsetX = p.getX() + objX;
		double offsetY = p.getY() + objY;
		objX += (1 - zoomRatio) * offsetX;
		objY += (1 - zoomRatio) * offsetY;
		//float resizing = (1 - zoomRatio) * ((float) v.getSize() * zoomRatio);
		sketch.circle((float) (objX), (float) (objY), (float) (coordinateObject.getSize() * zoomRatio));
	}
}
