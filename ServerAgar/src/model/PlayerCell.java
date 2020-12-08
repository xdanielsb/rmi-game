package model;

public class PlayerCell extends FeedableObject{

	private static final long serialVersionUID = 1L;

	public static final int CELL_MIN_SIZE = 50;

	private float repulsionX;
	private float repulsionY;
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
		repulsionX = 0;
		repulsionY = 0;
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

	public void addRepulsionX(float repulsionX) {
		this.repulsionX += repulsionX;
	}

	public void addRepulsionY(float repulsionY) {
		this.repulsionY += repulsionY;
	}

	@Override
	public float getSpeedX() {
		return super.getSpeedX() + repulsionX + movementX;
	}

	@Override
	public float getSpeedY() {
		return super.getSpeedY() + repulsionY + movementY;
	}

	@Override
	public void applyMouvement() {
		super.applyMouvement();
		repulsionX = 0;
		repulsionY = 0;
	}

}
