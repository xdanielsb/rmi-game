package client.displayer;

import interfaces.Board;
import processing.core.PApplet;

public class OuterBoundsDisplayer {
	public static void draw(Board board, double x, double y, PApplet sketch) {
		int outerHover = 1000;
		
		sketch.noFill();
		sketch.strokeWeight(outerHover);
		sketch.stroke(100);

		sketch.rect(-outerHover / 2, 
					-outerHover / 2, 
					outerHover + board.getBoardWidth(), 
					outerHover + board.getBoardHeight());
		
		sketch.stroke(255, 0, 0);
		sketch.strokeWeight(1);
		sketch.rect(0, 0, board.getBoardWidth(), board.getBoardHeight());
		sketch.stroke(0, 0, 0);
	}

}
