package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable{

	private List<Player> players;
	private int id;
	private int score;
	private Color color;
	
	private int spawnX, spawnY;
	
	public Team(int id, Color color, int spawnX, int spawnY) {
		this.id = id;
		this.color = color;
		this.score = 0;
		this.players = new ArrayList<>();
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean addPlayer(Player player) {
		return players.add(player);
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
	
	public int size() {
		return players.size();
	}
	
	public int getSpawnX() {
		return spawnX;
	}
	
	public int getSpawnY() {
		return spawnY;
	}
	
}
