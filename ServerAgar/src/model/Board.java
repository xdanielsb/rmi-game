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

	private Map<Integer, Player> players;
	private List<Team> teams;

	private int boardHeight, boardWidth;

	private Team winners;

	public Board(int boardLength, int boardWidth, int nbFood) {
		foods = new ArrayList<Food>();
		players = new HashMap<Integer, Player>();
		teams = new ArrayList<Team>();

		this.boardHeight = boardLength;
		this.boardWidth = boardWidth;

		players = new HashMap<Integer, Player>();

		for(int i = 0; i < nbFood; i++) {
			foods.add(new Food(this));
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

}
