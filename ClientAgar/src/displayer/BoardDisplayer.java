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

//package displayer;
//
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import model.Board;
//import model.FeedableObject;
//import model.Food;
//import model.Player;
//import model.SpikeCell;
//import processing.core.PApplet;
//
//public class BoardDisplayer {
//	
//	double screenSize;
//	double initialPlayerScreenProportion;
//	double initialPlayerSize;
//	double maximumPlayerScreenProportion;
//	double maximumPlayerSize;
//	
//	double maximumPlayerSizeMargeRatio;
//	
//	public BoardDisplayer(
//		double screenSize,
//		double initialPlayerScreenProportion,
//		double initialPlayerSize,
//		double maximumPlayerScreenProportion,
//		double maximumPlayerSize
//	) {
//		this.screenSize = screenSize;
//		this.initialPlayerScreenProportion = initialPlayerScreenProportion;
//		this.initialPlayerSize = initialPlayerSize;
//		this.maximumPlayerScreenProportion = maximumPlayerScreenProportion;
//		this.maximumPlayerSize = maximumPlayerSize;
//		
//		maximumPlayerSizeMargeRatio = Math.sqrt(maximumPlayerSize - initialPlayerSize);
//	}
//	
//	public void draw(Board board, Player player, float centerX, float centerY, PApplet sketch) {
//		float playerX = 0;
//		float playerY = 0;
//		List<Food> boardFoods = new ArrayList<>();
//		Collection<Player> boardPlayers = new ArrayList<>();
//		List<SpikeCell> boardSpikeCells = new ArrayList<>();
//		try{
//			playerX = player.getX();
//			playerY = player.getY();
//			boardFoods = board.getFoods();
//			boardPlayers = board.getPlayers();
//			boardSpikeCells = board.getSpikeCells();			
//		}catch(RemoteException e) {
//			e.printStackTrace();
//		}
//		
//		sketch.pushMatrix();
//		float zoomRatio = calculateScale(player);
//		sketch.scale(zoomRatio);
//
//		sketch.translate(
//				(centerX/zoomRatio) - playerX,
//				(centerY/zoomRatio) - playerY
//		);
//		
//		for(Food food : boardFoods) {
//			try {
//				if(food.isAlive()) {
//					FoodDisplayer.draw(food, sketch);		
//				}
//			} catch(RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		ArrayList<FeedableObject> cells = new ArrayList<>();
//		for(Player p : boardPlayers) {
//			try {
//				if(p.isAlive()) {						
//					cells.addAll(p.getCells());
//				}
//			} catch(RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		cells.addAll(boardSpikeCells);
//		
//		Collections.sort(cells);
//		
//		for(FeedableObject cellToDraw : cells) {
//			FeedableDisplayer.draw(cellToDraw, sketch);
//		}
//
//		OuterBoundsDisplayer.draw(
//				board, 
//				playerX, 
//				playerY, 
//				sketch
//		);
//		
//		sketch.popMatrix();
//	}
//	
//	public float calculateScale(Player player) {
//		
//		float playerSize = 0;
//		try {
//			playerSize = player.getSize();
//		} catch(RemoteException e) {
//			e.printStackTrace();
//		}
//		
//		double playerScreenProportion;
//		
//		if(player.getSize() < initialPlayerSize) {
//			
//			playerScreenProportion = initialPlayerScreenProportion;
//			
//		} else if(player.getSize() > maximumPlayerSize){
//			
//			playerScreenProportion = maximumPlayerScreenProportion;
//			
//		} else {			
//			
//			playerScreenProportion = 
//					initialPlayerScreenProportion +
//					(maximumPlayerScreenProportion-initialPlayerScreenProportion)*
//					(Math.sqrt(player.getSize()-initialPlayerSize)/maximumPlayerSizeMargeRatio);
//			
//		}
//		
//		double newScreenSize = player.getRadius()*2/playerScreenProportion;
//		return (float)(screenSize/newScreenSize);
//	}
//
//}
