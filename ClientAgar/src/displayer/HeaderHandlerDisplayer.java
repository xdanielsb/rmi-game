package displayer;

import processing.core.PApplet;
import processing.core.PConstants;
import view.HeaderHandler;

public class HeaderHandlerDisplayer {
	public static void draw(HeaderHandler headerHandler, PApplet sketch) {
		float headerHeight = sketch.height*0.07f;
		sketch.textAlign(PConstants.CENTER);
		sketch.fill(0);
		sketch.textSize(24);
		sketch.text("Timer : " + (int) headerHandler.gameTimer, sketch.width/2, headerHeight);
		sketch.fill(255, 0, 0);
		sketch.textSize(20);
		sketch.text("Score : " + headerHandler.xP, sketch.width/4, headerHeight);
		sketch.fill(0, 0, 255);
		sketch.textSize(20);
		sketch.text("Score : " + headerHandler.yP, sketch.width*0.75f, headerHeight);
	}

}
