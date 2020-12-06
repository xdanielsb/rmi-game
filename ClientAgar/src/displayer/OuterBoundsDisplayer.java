package displayer;

import model.Board;
import processing.core.PApplet;

public class OuterBoundsDisplayer {
	public static void draw(Board board, double x, double y, float zoom, PApplet sketch) {
		int outerHover = 1000;
		
		sketch.noFill();
		sketch.strokeWeight(outerHover);
		sketch.stroke(100);
		
		float dx = (float)((1 - zoom) * x);
		float dy = (float)((1 - zoom) * y);
		
		float resizingWidth = (1 - zoom) * (board.getBoardWidth());
		float resizingHeight = (1 - zoom) * (board.getBoardHeight());

		sketch.rect(dx - outerHover / 2, 
					dy - outerHover / 2, 
					outerHover + board.getBoardWidth() - resizingWidth, 
					outerHover + board.getBoardHeight() - resizingHeight);
		
		sketch.stroke(255, 0, 0);
		sketch.strokeWeight(1);
		sketch.rect(dx, dy, board.getBoardWidth() - resizingWidth, board.getBoardHeight() - resizingHeight);
		sketch.stroke(0, 0, 0);
	}

}
