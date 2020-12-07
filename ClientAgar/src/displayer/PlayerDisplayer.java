package displayer;

import java.awt.Color;

import model.Player;
import model.PlayerCell;
import processing.core.PApplet;

public class PlayerDisplayer {
	public static void draw(Player player, PApplet sketch)
	{
		sketch.pushMatrix();
		sketch.translate((float)player.getX(), (float)player.getY());
		
		int textSize = (int)(15 - (5f*PlayerCell.CELL_MIN_SIZE/player.getSize()));
		
		
		Color playerColor = player.getCell().getColor();
		sketch.fill(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue());	
		sketch.circle(0, 0, (float)(player.getSize()));
		sketch.fill(255);
		sketch.textSize(textSize);
		sketch.text(player.getName(), 0, 0-textSize);
		sketch.text((int)player.getSize(), 0, 0+textSize);
		
		sketch.popMatrix();
	}
}
