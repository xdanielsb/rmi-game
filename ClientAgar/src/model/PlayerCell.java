package model;

public interface PlayerCell extends FeedableObject {

	static final long serialVersionUID = 1L;

	public static final int CELL_MIN_SIZE = 50;

	public Player getPlayer();

}
