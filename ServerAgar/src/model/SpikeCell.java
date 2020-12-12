package model;

import java.awt.Color;

public class SpikeCell extends FeedableObject {

	private static final long serialVersionUID = 1L;
	
	public static final Color SPIKE_COLOR = new Color(0, 255, 0);
	public static final int MIN_SPIKE_SIZE = 400;
	public static final int MAX_SPIKE_SIZE = 800;
	public static final int EXPLOSION_NUMBER = 20;
	public static final float EXPLOSION_INITIAL_RATIO = 0.1f;
	
	public SpikeCell(Board board) {
		super(
				((float)Math.random() * (board.getBoardWidth())),
				((float)Math.random() * (board.getBoardHeight())),
				MIN_SPIKE_SIZE,
				SPIKE_COLOR
		);
	}

	public SpikeCell(SpikeCell cell, int size, float directionX, float directionY) {
		super(
				cell.getX(),
				cell.getY(),
				size,
				SPIKE_COLOR
		);
		cell.setSize(cell.getSize() - this.getSize());
		this.setInertiaX(directionX*5);
		this.setInertiaY(directionY*5);
	}
	
	@Override
	public boolean collideWith(FeedableObject fo) {
		if(fo instanceof PlayerCell) {
			return collideWithPlayer((PlayerCell)fo);
		} else if(fo instanceof SpikeCell) {
			return collideWithSpike((SpikeCell)fo);
		}
		return false;
	}
	
	public boolean collideWithPlayer(PlayerCell cell) {
		return false;
	}

	public boolean collideWithSpike(SpikeCell cell) {
		return true;
	}

}
