package displayer;

import processing.core.PApplet;
import view.HeaderHandler;

public class HeaderHandlerDisplayer {
	public static void draw(HeaderHandler headerHandler, PApplet sketch) {
		sketch.textAlign(sketch.CENTER);
		sketch.fill(0);
		sketch.textSize(24);
		sketch.text("Timer : " + (int) headerHandler.gameTimer, 0, 30, 800, 100);
		sketch.fill(255, 0, 0);
		sketch.textSize(20);
		sketch.text("Score " + headerHandler.xP, 0, 30, 400, 100);
		sketch.fill(0, 0, 255);
		sketch.textSize(20);
		sketch.text("Score " + headerHandler.yP, 400, 30, 400, 100);
	}

}
