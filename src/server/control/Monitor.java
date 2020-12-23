package server.control;

import java.util.ArrayList;
import java.util.List;

import interfaces.Food;
import interfaces.Player;
import interfaces.PlayerCell;
import interfaces.Team;

/**
 * This class will handle all synchronization for variables update
 */
public class Monitor {

	private List<Player> waitingAddPlayers;
	private Object addPlayersBell;

	private List<Player> waitingRemovePlayers;
	private Object removePlayersBell;
	
	private List<Food> waitingFoods;
	private Object foodsBell;
	
	private List<PlayerCell> waitingPlayerCells;
	private Object playerCellsBell;
	
	/**
	 * Monitor main constructor
	 */
	public Monitor() {
		waitingAddPlayers = new ArrayList<>();
		addPlayersBell = new Object();

		waitingRemovePlayers = new ArrayList<>();
		removePlayersBell = new Object();
		
		waitingFoods = new ArrayList<>();
		foodsBell = new Object();
		
		waitingPlayerCells = new ArrayList<>();
		playerCellsBell = new Object();
	}
	
	/**
	 * Method to synchronized all future players add on the board
	 * @param player : player to add
	 */
	public void addPlayer(Player player) {
		if(player == null) {
			return;
		}
		synchronized(addPlayersBell){			
			waitingAddPlayers.add(player);
		}
	}
	
	/**
	 * Method to know there is player to add on the board
	 * @return true if there is player to add on the board
	 */
	public boolean hasAddPlayerWaiting() {
		return waitingAddPlayers.size() > 0;
	}
	
	/**
	 * Method to get all the players to add on the board (will remove all the waiting players from the monitor)
	 * @return the list of player to add
	 */
	public List<Player> clearWaitingAddPlayers(){
		synchronized(addPlayersBell){		
			List<Player> tmp = new ArrayList<>();
			tmp.addAll(waitingAddPlayers);
			waitingAddPlayers.clear();
			return tmp;
		}
	}
	
	/**
	 * Method to synchronized all future players remove on the board
	 * @param player : player to add
	 */
	public void removePlayer(Player player) {
		if(player == null) {
			return;
		}
		synchronized(removePlayersBell){			
			waitingRemovePlayers.add(player);
		}
	}
	
	/**
	 * Method to know there is player to remove on the board
	 * @return true if there is player to remove on the board
	 */
	public boolean hasRemovePlayerWaiting() {
		return waitingRemovePlayers.size() > 0;
	}
	
	/**
	 * Method to get all the players to remove on the board (will remove all the waiting players from the monitor)
	 * @return the list of player to add
	 */
	public List<Player> clearWaitingRemovePlayers(){
		synchronized(removePlayersBell){		
			List<Player> tmp = new ArrayList<>();
			tmp.addAll(waitingRemovePlayers);
			waitingRemovePlayers.clear();
			return tmp;
		}
	}

	/**
	 * Method to synchronized all future foods add on the board
	 * @param foods : list of foods to add
	 */
	public void addFoods(List<Food> foods) {
		if(foods == null) {
			return;
		}
		synchronized(foodsBell){			
			waitingFoods.addAll(foods);
		}
	}
	
	/**
	 * Method to know there is food to add on the board
	 * @return true if there is food to add on the board
	 */
	public boolean hasFoodWaiting() {
		return waitingFoods.size() > 0;
	}
	
	/**
	 * Method to get all the foods to add on the board (will remove all the waiting foods from the monitor)
	 * @return the list of food to add
	 */
	public List<Food> clearWaitingFoods(){
		synchronized(foodsBell){			
			List<Food> tmp = new ArrayList<>();
			tmp.addAll(waitingFoods);
			waitingFoods.clear();
			return tmp;
		}
	}
	
	/**
	 * Method to synchronized all future player cells add on a player
	 * @param cells : list of player cells to add
	 */
	public void addPlayerCells(List<PlayerCell> cells) {
		if(cells == null) {
			return;
		}
		synchronized(playerCellsBell){			
			waitingPlayerCells.addAll(cells);
		}
	}
	
	/**
	 * Method to know there is player cell to add in a player
	 * @return true if there is player cell to add in a player
	 */
	public boolean hasPlayerCellWaiting() {
		return waitingPlayerCells.size() > 0;
	}
	
	/**
	 * Method to get all the player cells to add in a player (will remove all the waiting player cells from the monitor)
	 * @return the list of player cells to add
	 */
	public List<PlayerCell> clearWaitingPlayerCells(){
		synchronized(playerCellsBell){			
			List<PlayerCell> tmp = new ArrayList<>();
			tmp.addAll(waitingPlayerCells);
			waitingPlayerCells.clear();
			return tmp;
		}
	}
	
	/**
	 * Method to update a team score
	 * @param team : Team who will have it score updated
	 * @param amount : value to add to the score (positive or negative)
	 */
	public void addScore(Team team, int amount) {
		if(team == null) {
			return;
		}
		synchronized (team.getBell()) {
			team.addToScore(amount);
		}
	}

};
