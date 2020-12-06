package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Serializable{
	
	private int nbFood;
	private List<Food> foods;
	
	private Map<Integer, Player> players;
	private Map<Integer, Team> teams;
		
	private int boardHeight, boardWidth;
	
	private Team winners;
	
	public Board(int boardLength, int boardWidth, int nbFood) {
		this.foods = new ArrayList<Food>();
		players = new HashMap<Integer, Player>();
		teams = new HashMap<Integer, Team>();
		
		this.boardHeight = boardLength;
		this.boardWidth = boardWidth;
		
		players = new HashMap<Integer, Player>();
		
		this.nbFood = nbFood;
		for(int i = 0; i < nbFood; i++) {
			foods.add(new Food(boardLength, boardWidth));
		}
		
		winners = null;
		
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getNbFood() {
		return nbFood;
	}
	
	public void addTeam(Team team) {
		teams.put(team.getId(), team);
	}
	
	public Team getTeam(int team) {
		return teams.get(team);
	}
	
	public int getNbTeam() {
		return teams.size();
	}
	
	public void addPlayer(Player player) {
		players.put(player.getId(), player);
		if(teams.size() <= 0) {
			return;
		}
		Team minTeam = null;
		int min = Integer.MAX_VALUE;
		for(int t : teams.keySet()) {
			if(teams.get(t).size() < min) {
				min = teams.get(t).size();
				minTeam = teams.get(t);
			}
		}
		minTeam.addPlayer(player);
		player.setTeam(minTeam);
	}
	
	public void removePlayer(Player player) {
		player.getTeam().removePlayer(player);
		players.remove(player.getId());
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	public Collection<Player> getPlayers() {
		return players.values();
	}
	
	public Player removePlayer(int id) {
		Player player = players.remove(id);
		player.getTeam().removePlayer(player);
		return player;
	}
	
	public Team getWinners() {
		return winners;
	}

	public void setWinner() {
		Team winning = this.teams.get(0);
		int higherScore = winning.getScore();
		for(Team team : teams.values())
		{
			if(team.getScore() > higherScore)
			{
				winning = team;
				higherScore = winning.getScore();
			}
		}
		this.winners = winning;		
	}
	
	public List<Food> getFoods(){
		return foods;
	}
	
	public void removeFood(List<Food> eatenFood) {
		for(Food f : eatenFood) {
			f.DisableFood();
		}
	}

}
