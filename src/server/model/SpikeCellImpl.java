package server.model;

import interfaces.Board;
import interfaces.FeedableObject;
import interfaces.PlayerCell;
import interfaces.SpikeCell;

public class SpikeCellImpl extends FeedableObjectImpl implements SpikeCell {

	private static final long serialVersionUID = 1L;

	public SpikeCellImpl(Board board) {
		super(
				((float)Math.random() * (board.getBoardWidth())),
				((float)Math.random() * (board.getBoardHeight())),
				MIN_SPIKE_SIZE,
				SPIKE_COLOR
		);
	}

	public SpikeCellImpl(SpikeCell cell, int size, float directionX, float directionY) {
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
