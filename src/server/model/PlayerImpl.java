package server.model;

import java.util.ArrayList;
import java.util.List;

import interfaces.Player;
import interfaces.PlayerCell;
import interfaces.Team;

public class PlayerImpl implements Player {

	private static final long serialVersionUID = 1L;

	private final String name;

	private Team team;
	private List<PlayerCell> cells;
	private int id;
	private boolean alive;

	public PlayerImpl(int idP, String name) {
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
		cells = new ArrayList<>();
		cells.add(new PlayerCellImpl(this, PlayerCell.CELL_MIN_SIZE));
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
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for(PlayerCell cell : cells) {
			float radius = cell.getRadius();
			float x = cell.getX();
			if(x-radius < min) {
				min = x-radius;
			}
			if(x+radius > max) {
				max = x+radius;
			}
		}
		return (min+max)/2;
	}

	public float getY() {
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for(PlayerCell cell : cells) {
			float radius = cell.getRadius();
			float y = cell.getY();
			if(y-radius < min) {
				min = y-radius;
			}
			if(y+radius > max) {
				max = y+radius;
			}
		}
		return (min+max)/2;
	}

	public int getSize() {
		int somme = 0;
		for(PlayerCell cell : cells) {
			somme += cell.getSize();
		}
		return somme;
	}
	
	public float getRadius() {
		return (float)Math.sqrt(getSize()/Math.PI);
	}

	public List<PlayerCell> getCells(){
		return cells;
	}

	public void addCell(PlayerCell cell) {
		cells.add(cell);
	}

	public boolean removeCell(PlayerCell cell) {
		return cells.remove(cell);
	}

}
