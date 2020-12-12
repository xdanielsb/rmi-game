package displayer;

import java.awt.Color;

import model.FeedableObject;
import model.PlayerCell;
import model.SpikeCell;
import processing.core.PApplet;
import processing.core.PConstants;

public class FeedableDisplayer {
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
	
	public static void draw(SpikeCell cell, PApplet sketch) {
		sketch.pushMatrix();
		sketch.translate(cell.getX(), cell.getY());
		
		Color color = cell.getColor();
		sketch.fill(color.getRed(), color.getGreen(), color.getBlue());
		sketch.circle(0, 0, cell.getRadius()*2);
		
		sketch.popMatrix();
	}
	
	public static void draw(FeedableObject cell, PApplet sketch) {
		if(cell instanceof PlayerCell) {
			draw((PlayerCell)cell, sketch);
		} else if(cell instanceof SpikeCell){
			draw((SpikeCell)cell, sketch);
		}
	}
}
