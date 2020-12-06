package model;

import java.io.Serializable;

public class Player implements Serializable {
	
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
		cell = new PlayerCell(team.getSpawnX(), team.getSpawnY(), PlayerCell.CELL_MIN_SIZE);
		cell.setColor(team.getColor());
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
	
	public double getX() {
		return this.cell.getX();
	}
	
	public void setX(double x) {
		this.cell.setX(x);
	}
	
	public double getY() {
		return this.cell.getY();
	}
	
	public void setY(double y) {
		this.cell.setY(y);
	}
	
	public void setSize(double size) {
		this.cell.setSize(size);
	}
	
	public double getSize() {
		return this.cell.getSize();
	}
	
	public PlayerCell getCell() {
		return this.cell;
	}
	
	public double dist(Player p) {
		return this.cell.dist(p.getCell());
	}
	
}
