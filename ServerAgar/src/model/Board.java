package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Food> foods;
	private List<SpikeCell> spikes;

	private Map<Integer, Player> players;
	private List<Team> teams;

	private int boardHeight, boardWidth;

	private Team winners;

	public Board(int boardLength, int boardWidth, int nbFood, int nbSpike) {
		foods = new ArrayList<>();
		spikes = new ArrayList<>();
		players = new HashMap<>();
		teams = new ArrayList<>();

		this.boardHeight = boardLength;
		this.boardWidth = boardWidth;

		players = new HashMap<>();

		for(int i = 0; i < nbFood; i++) {
			foods.add(new Food(this));
		}

		for(int i = 0; i < nbSpike; i++) {
			spikes.add(new SpikeCell(this));
		}

		winners = null;

	}

	public int getBoardHeight() {
		return boardHeight;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void addTeam(Team team) {
		teams.add(team);
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

	public void addPlayer(Player player) {
		players.put(player.getId(), player);
		if(teams.size() <= 0) {
			return;
		}
		Team minTeam = null;
		int min = Integer.MAX_VALUE;
		for(Team t : teams) {
			if(t.getPlayers().size() < min) {
				min = t.getPlayers().size();
				minTeam = t;
			}
		}
		minTeam.addPlayer(player);
		player.setTeam(minTeam);
	}

	public Team getWinners() {
		return winners;
	}

	public void setWinner() {
		Team winning = null;
		int higherScore = -1;
		for(Team team : teams) {
			if(team.getScore() > higherScore) {
				winning = team;
				higherScore = winning.getScore();
			}
		}
		this.winners = winning;		
	}

	public List<Food> getFoods(){
		return foods;
	}
	
	public void addFoods(List<Food> foods) {
		this.foods.addAll(foods);
	}
	
	public boolean removeFood(Food food) {
		return foods.remove(food);
	}

	public void addSpike(SpikeCell spike) {
		this.spikes.add(spike);
	}
	
	public boolean removeSpike(SpikeCell spike) {
		return spikes.remove(spike);
	}
	
	public List<SpikeCell> getSpikeCells(){
		return spikes;
	}

}
