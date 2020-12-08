package model;

public class PlayerCell extends FeedableObject{

	private static final long serialVersionUID = 1L;
	
	public static final int CELL_MIN_SIZE = 50;
	
	private double repulsionX;
	private double repulsionY;
	private double movementX;
	private double movementY;
	
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
	
	public void setMovementX(double movementX) {
		this.movementX = movementX;
	}

	public void setMovementY(double movementY) {
		this.movementY = movementY;
	}
	
	public double getRepulsionX() {
		return repulsionX;
	}
	
	public void addRepulsionX(double repulsionX) {
		this.repulsionX += repulsionX;
	}
	
	public double getRepulsionY() {
		return repulsionY;
	}

	public void addRepulsionY(double repulsionY) {
		this.repulsionY += repulsionY;
	}
	
	@Override
	public double getSpeedX() {
		return super.getSpeedX() + repulsionX + movementX;
	}

	@Override
	public double getSpeedY() {
		return super.getSpeedY() + repulsionY + movementY;
	}
	
	@Override
	public void applyMouvement() {
		super.applyMouvement();
		repulsionX = 0;
		repulsionY = 0;
	}
	
}
