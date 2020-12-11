package model;

public class PlayerCell extends FeedableObject {

	private static final long serialVersionUID = 1L;

	public static final int CELL_MIN_SIZE = 50;
	public static final int MIN_THROWING_FOOD_SIZE = 400;
	public static final int MIN_SPLITTING_SIZE = 100;

	private float movementX;
	private float movementY;

	public Player player;

	public PlayerCell(Player player, int size) {
		super(
				player.getTeam().getSpawnX(),
				player.getTeam().getSpawnY(),
				size,
				player.getTeam().getColor()
		);
		movementX = 0;
		movementY = 0;
		this.player = player;
	}
	
	public PlayerCell(PlayerCell cell, float ratio, float directionX, float directionY) {
		super(
				cell.getX() + cell.getRadius()*directionX,
				cell.getY() + cell.getRadius()*directionY,
				(int)(cell.getSize()*ratio),
				cell.getPlayer().getTeam().getColor()
		);
		cell.setSize(cell.getSize() - this.getSize());
		movementX = 0;
		movementY = 0;
		player = cell.getPlayer();
	}

	public Player getPlayer() {
		return player;
	}

	public void setMovementX(float movementX) {
		this.movementX = movementX;
	}

	public void setMovementY(float movementY) {
		this.movementY = movementY;
	}

	@Override
	public float getSpeedX() {
		return super.getSpeedX() + movementX;
	}

	@Override
	public float getSpeedY() {
		return super.getSpeedY() + movementY;
	}
	
	
}
