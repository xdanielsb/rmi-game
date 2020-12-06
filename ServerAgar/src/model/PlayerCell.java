package model;

public class PlayerCell extends FeedableObject{

	public static final int CELL_MIN_SIZE = 50;
	
	public PlayerCell(double x, double y, double size) {
		super(x, y, size);
	}
	
	public void moveTo(double movingX, double movingY) {
		this.setX(this.getX()+movingX);
		this.setY(this.getY()+movingY);
	}
	
}
