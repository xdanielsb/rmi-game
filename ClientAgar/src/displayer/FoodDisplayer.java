package displayer;

import java.awt.Color;

import model.Food;
import processing.core.PApplet;

public class FoodDisplayer {

	public static void draw(Food food, PApplet sketch)
	{
		Color foodColor = food.getColor();
		sketch.fill(foodColor.getRed(), foodColor.getGreen(), foodColor.getBlue());
		float diameter;
		if(food.isPersistent()) {
			diameter = 3;
		} else {
			diameter = food.getRadius() * 2;
		}
		sketch.circle((float) (food.getX()), (float) (food.getY()), diameter);
	}
}
