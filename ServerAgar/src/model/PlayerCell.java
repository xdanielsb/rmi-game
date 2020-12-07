package model;

public class PlayerCell extends FeedableObject{

	private static final long serialVersionUID = 1L;
	
	public static final int CELL_MIN_SIZE = 50;
	
	private double repulsionX, repulsionY;
	
	public PlayerCell(double x, double y, double size) {
		super(x, y, size);
		repulsionX = 0;
		repulsionY = 0;
	}
	
	public void moveTo(double movingX, double movingY) {
		this.setX(this.getX()+movingX);
		this.setY(this.getY()+movingY);
	}
	
	public double getRepulsionX() {
		return repulsionX;
	}
	
	public void setRepulsionX(double repulsionX) {
		this.repulsionX = repulsionX;
	}
	
	public double getRepulsionY() {
		return repulsionY;
	}

	public void setRepulsionY(double repulsionY) {
		this.repulsionY = repulsionY;
	}
	
	public void proceedRepulsion() {
		setX(getX()+repulsionX);
		setY(getY()+repulsionY);
		repulsionX *= 0.5;
		if(repulsionX < 0.1) {
			setRepulsionX(0);
		}
		repulsionY *= 0.5;
		if(repulsionY < 0.1) {
			setRepulsionY(0);
		}
	}
	
}
