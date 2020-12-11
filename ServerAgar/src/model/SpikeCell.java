package model;

import java.awt.Color;

public class SpikeCell extends FeedableObject {

	private static final long serialVersionUID = 1L;
	
	public static final Color SPIKE_COLOR = new Color(0, 255, 0);
	public static final int MIN_SPIKE_SIZE = 400;
	public static final int MAX_SPIKE_SIZE = 800;
	
	public SpikeCell(Board board) {
		super(
				((float)Math.random() * (board.getBoardWidth())),
				((float)Math.random() * (board.getBoardHeight())),
				MIN_SPIKE_SIZE,
				SPIKE_COLOR
		);
	}

}
