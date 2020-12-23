package server.model;

import java.util.ArrayList;
import java.util.List;

import interfaces.Player;
import interfaces.PlayerCell;
import interfaces.Team;

/**
 * Implementation of the Player Interface
 */
public class PlayerImpl implements Player {

	private static final long serialVersionUID = 1L;

	private final String name;

	private Team team;
	private List<PlayerCell> cells;
	private int id;
	private float x;
	private float y;
	private boolean alive;

	/**
	 * Player main constructor
	 * @param idP : int id of the player
	 * @param name : name of the player
	 */
	public PlayerImpl(int idP, String name) {
		this.name = name;
		this.id = idP;
		x = 0;
		y = 0;
		this.alive = true;
	}

	/**
	 * Method to get the player id
	 * @return id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Method to get the player Team
	 * @return team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Method to modify the player team
	 * @param team : new team
	 */
	public void setTeam(Team team) {
		this.team = team;
		cells = new ArrayList<>();
		cells.add(new PlayerCellImpl(this, PlayerCell.CELL_MIN_SIZE));
		updateCoordinates();
	}

	/**
	 * Method to get ce player name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to modify the life status of a player
	 * @param alive : true = le player is now alive; false = the player is now dead
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * Method to verify if the player is still alive
	 * @return true if the player is alive, false if not
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Method to get the position of the player on X coordinate ( globally equals to it cells position average)
	 * @return X position
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Method to get the position of the player on Y coordinate ( globally equals to it cells position average)
	 * @return Y position
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Method to calculate the coordinate of the player, to avoid multiple calculation each time that we need this coordinates
	 */
	public void updateCoordinates() {
		float maxX = Float.MIN_VALUE;
		float minX = Float.MAX_VALUE;
		float maxY = Float.MIN_VALUE;
		float minY = Float.MAX_VALUE;
		for(PlayerCell cell : cells) {
			float radius = cell.getRadius();
			float cellX = cell.getX();
			float cellY = cell.getY();
			if(cellX-radius < minX) {
				minX = cellX-radius;
			}
			if(cellX+radius > maxX) {
				maxX = cellX+radius;
			}
			if(cellY-radius < minY) {
				minY = cellY-radius;
			}
			if(cellY+radius > maxY) {
				maxY = cellY+radius;
			}
		}
		x = (minX+maxX)/2;
		y = (minY+maxY)/2;
	}

	/**
	 * Method to get the player size (as the sum of the size of all it cells)
	 * @return size
	 */
	public int getSize() {
		int somme = 0;
		for(PlayerCell cell : cells) {
			somme += cell.getSize();
		}
		return somme;
	}
	
	/**
	 * Method to get the player radius ( sqrt(player.size/PI) )
	 * @return radius
	 */
	public float getRadius() {
		return (float)Math.sqrt(getSize()/Math.PI);
	}

	/**
	 * Method to get the list of a player's cells
	 * @return PlayerCell list
	 */
	public List<PlayerCell> getCells(){
		return cells;
	}

	/**
	 * Method to add a PlayerCell to a Player
	 * @param cell : PlayerCell to add
	 */
	public void addCell(PlayerCell cell) {
		cells.add(cell);
	}

	/**
	 * Method to remove a PlayerCell to a player
	 * @param cell : PlayerCell to remove
	 * @return true if the PlayerCell is remove, false if not
	 */
	public boolean removeCell(PlayerCell cell) {
		return cells.remove(cell);
	}

}
