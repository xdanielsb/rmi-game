package displayer;

import java.awt.Color;

import model.Player;
import model.PlayerCell;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class PlayerDisplayer {
	public static void draw(Player player, PApplet sketch)
	{
		sketch.pushMatrix();
		sketch.translate((float)player.getX(), (float)player.getY());
		
		float textSize = (int)(1 - (5f*PlayerCell.CELL_MIN_SIZE/(player.getCell().getRadius()*2)));
		
		
		Color playerColor = player.getCell().getColor();
		sketch.fill(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue());
		sketch.circle(0, 0, (float)(player.getCell().getRadius())*2);
		sketch.fill(255);
		sketch.textAlign(PConstants.CENTER);
		sketch.textSize(textSize);
		sketch.text(player.getName(), -textSize/6, -textSize);
		sketch.text((int)player.getSize(), -textSize/6, textSize);
		
		sketch.popMatrix();
	}
}
