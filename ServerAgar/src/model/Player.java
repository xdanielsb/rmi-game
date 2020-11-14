package model;

import java.awt.Color;
import java.io.Serializable;

public class Player extends SpaceObject implements Serializable {
	private final int team;
	private final String name;

	public Player(int idP, int idT, String name) {		
		this.name = name;
		team = idT;
		if (idT == 0) {
			this.setX(50);
			this.setY(400);
		} else {

			this.setX(750);
			this.setY(400);
		}
		this.setSize(50);
		this.setId(idP);
		this.setAlive(true);
	}

	public int getTeam() {
		return team;
	}

	public String getName() {
		return name;
	}
}
