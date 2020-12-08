package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Player> players;
	private int score;
	private Color color;
	private String teamName;

	private int spawnX, spawnY;
	
	private transient final Object bell;

	public Team(Color color, String teamName, int spawnX, int spawnY) {
		this.color = color;
		this.score = 0;
		this.players = new ArrayList<>();
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.teamName = teamName;
		bell = new Object();
	}

	public Color getColor() {
		return color;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public boolean removePlayer(Player player) {
		return players.remove(player);
	}

	public List<Player> getPlayers(){
		return players;
	}

	public int getScore() {
		return score;
	}

	public void addToScore(int amount) {
		score += amount;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public String getTeamName() {
		return this.teamName;
	}
	
	public Object getBell() {
		return bell;
	}
}
