package displayer;

import java.util.ArrayList;
import java.util.Collections;

import model.Board;
import model.FeedableObject;
import model.Food;
import model.Player;
import processing.core.PApplet;

public class BoardDisplayer {
	
	double screenSize;
	double initialPlayerScreenProportion;
	double initialPlayerSize;
	double maximumPlayerScreenProportion;
	double maximumPlayerSize;
	
	double maximumPlayerSizeMargeRatio;
	
	public BoardDisplayer(
		double screenSize,
		double initialPlayerScreenProportion,
		double initialPlayerSize,
		double maximumPlayerScreenProportion,
		double maximumPlayerSize
	) {
		this.screenSize = screenSize;
		this.initialPlayerScreenProportion = initialPlayerScreenProportion;
		this.initialPlayerSize = initialPlayerSize;
		this.maximumPlayerScreenProportion = maximumPlayerScreenProportion;
		this.maximumPlayerSize = maximumPlayerSize;
		
		maximumPlayerSizeMargeRatio = Math.sqrt(maximumPlayerSize - initialPlayerSize);
	}
	
	public void draw(Board board, Player player, float centerX, float centerY, PApplet sketch) {
		sketch.pushMatrix();
		float zoomRatio = calculateScale(player);
		sketch.scale(zoomRatio);

		sketch.translate(
				(centerX/zoomRatio) - player.getX(),
				(centerY/zoomRatio) - player.getY()
		);
		
		for(Food food : board.getFoods()) {
			if(food.isAlive()) {
				FoodDisplayer.draw(food, sketch);		
			}
		}
		
		ArrayList<FeedableObject> cells = new ArrayList<>();
		for(Player p : board.getPlayers()) {
			if(p.isAlive()) {						
				cells.addAll(p.getCells());
		
			}
		}
		cells.addAll(board.getSpikeCells());
		
		Collections.sort(cells);
		
		for(FeedableObject cellToDraw : cells) {
			FeedableDisplayer.draw(cellToDraw, sketch);
		}

		OuterBoundsDisplayer.draw(
				board, 
				player.getX(), 
				player.getY(), 
				sketch
		);
		
		sketch.popMatrix();
	}
	
	public float calculateScale(Player player) {
		double playerScreenProportion;
		
		if(player.getSize() < initialPlayerSize) {
			
			playerScreenProportion = initialPlayerScreenProportion;
			
		} else if(player.getSize() > maximumPlayerSize){
			
			playerScreenProportion = maximumPlayerScreenProportion;
			
		} else {			
			
			playerScreenProportion = 
					initialPlayerScreenProportion +
					(maximumPlayerScreenProportion-initialPlayerScreenProportion)*
					(Math.sqrt(player.getSize()-initialPlayerSize)/maximumPlayerSizeMargeRatio);
			
		}
		
		double newScreenSize = player.getRadius()*2/playerScreenProportion;
		return (float)(screenSize/newScreenSize);
	}

}
