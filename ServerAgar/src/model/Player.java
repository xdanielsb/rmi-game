package model;

import java.awt.Color;
import java.io.Serializable;

import processing.core.PApplet;

public class Player implements Serializable {
	private final int team;
	private final String name;
	private PlayerCell cell;
	private int id;
	private boolean alive;

	public Player(int idP, int idT, String name) {		
		this.name = name;
		team = idT;
		if (idT == 0) {
			cell = new PlayerCell(50, 400, 50);
			cell.setColor(new Color(255, 0, 0));
		} else {

			cell = new PlayerCell(750, 400, 50);
			cell.setColor(new Color(0, 0, 255));
		}

		this.id = idP;
		this.alive = true;
	}
	
	public int getId() {
		return this.id;
	}

	public int getTeam() {
		return team;
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
