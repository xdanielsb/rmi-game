package displayer;

import java.awt.Color;

import model.PlayerCell;
import processing.core.PApplet;
import processing.core.PConstants;

public class PlayerCellDisplayer {
	public static void draw(PlayerCell cell, PApplet sketch)
	{
		sketch.pushMatrix();
		sketch.translate(cell.getX(), cell.getY());
		
		float textSize = 3;//(int)(1 - (5f*PlayerCell.CELL_MIN_SIZE/(player.getCell().getRadius()*2)));
		
		Color playerColor = cell.getColor();
		sketch.fill(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue());
		sketch.circle(0, 0, cell.getRadius()*2);
		sketch.fill(255);
		sketch.textAlign(PConstants.CENTER);
		sketch.textSize(textSize);
		sketch.text(cell.getPlayer().getName(), -textSize/6, -textSize);
		sketch.text(cell.getSize(), -textSize/6, textSize);
		
		sketch.popMatrix();
	}
}
