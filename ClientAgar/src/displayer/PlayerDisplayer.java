package displayer;

import java.awt.Color;

import model.Player;
import processing.core.PApplet;

public class PlayerDisplayer {
	public static void draw(Player player, float zoomRatio, int cstX, int cstY, PApplet sketch)
	{
		Color playerColor = player.getCell().getColor();
		sketch.fill(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue());
		sketch.circle((float)player.getX()+cstX, (float)player.getY()+cstY, (float)(player.getSize() * zoomRatio));		
	}
}
