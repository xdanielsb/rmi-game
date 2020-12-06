package displayer;

import processing.core.PApplet;

public class OuterBoundsDisplayer {
	public static void draw(double x, double y, float zoom, int cstX, int cstY, PApplet sketch) {
		sketch.noFill();
		sketch.strokeWeight(5000);
		sketch.stroke(180, 180, 180);
		float dx = (float) (cstX);
		float dy = (float) (cstY);
		float offsetX = (float) x + cstX - dx;
		float offsetY = (float) y + cstY - dy;
		dx += (1 - zoom) * offsetX;
		dy += (1 - zoom) * offsetY;

		float resizing = (1 - zoom) * (1600);

		sketch.rect(dx - 2500, dy - 2500, 6600 - resizing, 6600 - resizing);
		sketch.stroke(255, 0, 0);
		sketch.strokeWeight(1);
		sketch.rect(dx, dy, 1600 - resizing, 1600 - resizing);
		sketch.stroke(0, 0, 0);
	}

}
