package model;

public class PlayerCellImpl extends FeedableObjectImpl implements PlayerCell {

	private static final long serialVersionUID = 1L;

	private float movementX;
	private float movementY;

	private int cooldown;

	private Player player;

	public PlayerCellImpl(Player player, int size) {
		super(
				player.getTeam().getSpawnX(),
				player.getTeam().getSpawnY(),
				size,
				player.getTeam().getColor()
		);
		movementX = 0;
		movementY = 0;
		cooldown = 0;
		this.player = player;
	}

	public PlayerCellImpl(PlayerCell cell, int size, float directionX, float directionY) {
		super(
				cell.getX(),
				cell.getY(),
				size,
				cell.getPlayer().getTeam().getColor()
		);
		cell.setSize(cell.getSize() - this.getSize());
		this.setInertiaX(directionX*4);
		this.setInertiaY(directionY*4);
		movementX = 0;
		movementY = 0;
		cooldown = COOLDOWN_INITIAL;
		cell.resetCooldown();
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
		return super.getSpeedX() + movementX * (MIN_SPEED + (1-MIN_SPEED)*((float)CELL_MIN_SIZE/(float)getSize()));
	}

	@Override
	public float getSpeedY() {
		return super.getSpeedY() + movementY * (MIN_SPEED + (1-MIN_SPEED)*((float)CELL_MIN_SIZE/(float)getSize()));
	}

	public void updateCooldown() {
		if(cooldown > 0) {
			cooldown -= 1;
		}
	}

	public int getCooldown() {
		return cooldown;
	}

	public void resetCooldown() {
		cooldown = COOLDOWN_INITIAL;
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
		return getPlayer().getTeam() == cell.getPlayer().getTeam() &&
				!(getPlayer() == getPlayer() && getCooldown() == 0 && cell.getCooldown() == 0);
	}

	public boolean collideWithSpike(SpikeCell cell) {
		return false;
	}

}
