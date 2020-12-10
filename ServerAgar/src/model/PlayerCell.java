package model;

public class PlayerCell extends FeedableObject{

	private static final long serialVersionUID = 1L;

	public static final int CELL_MIN_SIZE = 50;
	public static final int MIN_THROWING_FOOD_SIZE = 400;

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
