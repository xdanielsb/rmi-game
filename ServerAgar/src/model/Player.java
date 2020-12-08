package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable, Comparable<Player> {

	private static final long serialVersionUID = 1L;

	private final String name;

	private Team team;
	private PlayerCell cell;
	private int id;
	private boolean alive;

	public Player(int idP, String name) {
		this.name = name;
		this.id = idP;
		this.alive = true;
	}

	public int getId() {
		return this.id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
		cell = new PlayerCell(this, PlayerCell.CELL_MIN_SIZE);
	}

	public String getName() {
		return name;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public float getX() {
		if(cell == null) {
			return team.getSpawnX();
		}
		return this.cell.getX();
	}

	public float getY() {
		if(cell == null) {
			return team.getSpawnY();
		}
		return this.cell.getY();
	}

	public int getSize() {
		if(cell == null) {
			return 0;
		}
		return this.cell.getSize();
	}

	public PlayerCell getCell() {
		return this.cell;
	}

	public List<PlayerCell> getCells(){
		List<PlayerCell> cells = new ArrayList<>();
		if(cell != null) {			
			cells.add(cell);
		}
		return cells;
	}

	public void addCell(PlayerCell cell) {
		this.cell = cell;
	}

	public void removeCell(PlayerCell cell) {
		this.cell = null;
	}

	@Override
	public int compareTo(Player player) {
		return Double.compare(this.getSize(), player.getSize());
	}

}
