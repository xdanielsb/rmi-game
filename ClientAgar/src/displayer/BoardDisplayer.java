package displayer;

import java.util.ArrayList;
import java.util.Collections;

import model.Board;
import model.Food;
import model.Player;
import processing.core.PApplet;

public class BoardDisplayer {
	public static void draw(Board board, Player player, float zoomRatio, float centerX, float centerY, PApplet sketch) {
		sketch.pushMatrix();
		sketch.scale(zoomRatio);

		sketch.translate(
				(float)((centerX/zoomRatio) - player.getX()), 
				(float)((centerY/zoomRatio) - player.getY())
		);
		
		for(Food food : board.getFoods()) {
			if(food.isAlive()) {
				FoodDisplayer.draw(food, sketch);		
			}
		}
		
		ArrayList<Player> p = new ArrayList<Player>(board.getPlayers());
		Collections.sort(p);
		
		for(Player playerToDraw : p) {
			if(playerToDraw.isAlive()) {						
				PlayerDisplayer.draw(playerToDraw, sketch);
			}
		}

		OuterBoundsDisplayer.draw(
				board, 
				player.getX(), 
				player.getY(), 
				sketch
		);
		
		sketch.popMatrix();
	}

}
