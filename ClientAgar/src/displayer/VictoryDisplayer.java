package displayer;

import processing.core.PApplet;

public class VictoryDisplayer {
	public static void draw(int scoreTeamOne, int scoreTeamTwo, PApplet sketch)
	{
		sketch.textSize(80);
		sketch.textAlign(sketch.CENTER);
		sketch.fill(0);
		sketch.text("Team " + (scoreTeamOne >= scoreTeamTwo ? "Red" : "Blue") + " wins", sketch.width/2, sketch.height/2);
	}

}
