package server.model;

import interfaces.Player;
import interfaces.PlayerCell;
import interfaces.Team;
import java.util.ArrayList;
import java.util.List;

/** Implementation of the Player Interface */
public class PlayerImpl implements Player {

  private static final long serialVersionUID = 1L;

  private final String name;

  private Team team;
  private List<PlayerCell> cells;
  private int id;
  private float x;
  private float y;
  private boolean alive;
  
  private int size;
  private float radius;

  private float throwDirectionX;
  private float throwDirectionY;
  private float splitDirectionX;
  private float splitDirectionY;

  /**
   * Player main constructor
   *
   * @param idP : int id of the player
   * @param name : name of the player
   */
  public PlayerImpl(int idP, String name) {
    this.name = name;
    this.id = idP;
    x = 0;
    y = 0;
    this.alive = true;
    
    size = 0;
    radius = 0;
    
    throwDirectionX = 0;
    throwDirectionY = 0;
    splitDirectionX = 0;
    splitDirectionY = 0;
  }

  /**
   * Method to get the player id
   *
   * @return id
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * Method to get the player Team
   *
   * @return team
   */
  @Override
  public Team getTeam() {
    return team;
  }

  /**
   * Method to modify the player team
   *
   * @param team : new team
   */
  @Override
  public void setTeam(Team team) {
    this.team = team;
    cells = new ArrayList<>();
    cells.add(new PlayerCellImpl(this, PlayerCell.CELL_MIN_SIZE));
    updateCoordinates();
  }

  /**
   * Method to get ce player name
   *
   * @return name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Method to modify the life status of a player
   *
   * @param alive : true = le player is now alive; false = the player is now dead
   */
  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Method to verify if the player is still alive
   *
   * @return true if the player is alive, false if not
   */
  @Override
  public boolean isAlive() {
    return alive;
  }

  /**
   * Method to know direction the player want to throw some food (X coordinate)
   *
   * @return X coordinate
   */
  @Override
  public float getThrowDirectionX() {
    return throwDirectionX;
  }

  /**
   * Method to know direction the player want to throw some food (Y coordinate)
   *
   * @return X coordinate
   */
  @Override
  public float getThrowDirectionY() {
    return throwDirectionY;
  }

  /**
   * Method to modify the direction the player want to throw some food
   *
   * @param throwDirectionX : X coordinate
   * @param throwDirectionY : Y coordinate
   */
  @Override
  public void setThrowDirection(float throwDirectionX, float throwDirectionY) {
    this.throwDirectionX = throwDirectionX;
    this.throwDirectionY = throwDirectionY;
  }

  /**
   * Method to know direction the player want to split himself (X coordinate)
   *
   * @return X coordinate
   */
  @Override
  public float getSplitDirectionX() {
    return splitDirectionX;
  }

  /**
   * Method to know direction the player want to split himself (Y coordinate)
   *
   * @return X coordinate
   */
  @Override
  public float getSplitDirectionY() {
    return splitDirectionY;
  }

  /**
   * Method to modify the direction the player want to split hiwself
   *
   * @param splitDirectionX : X coordinate
   * @param splitDirectionY : Y coordinate
   */
  @Override
  public void setSplitDirection(float splitDirectionX, float splitDirectionY) {
    this.splitDirectionX = splitDirectionX;
    this.splitDirectionY = splitDirectionY;
  }

  /**
   * Method to get the position of the player on X coordinate ( globally equals to it cells position
   * average)
   *
   * @return X position
   */
  @Override
  public float getX() {
    return x;
  }

  /**
   * Method to get the position of the player on Y coordinate ( globally equals to it cells position
   * average)
   *
   * @return Y position
   */
  @Override
  public float getY() {
    return y;
  }

  /**
   * Method to calculate the coordinate of the player, to avoid multiple calculation each time that
   * we need this coordinates
   */
  @Override
  public void updateCoordinates() {
    float maxX = Float.MIN_VALUE;
    float minX = Float.MAX_VALUE;
    float maxY = Float.MIN_VALUE;
    float minY = Float.MAX_VALUE;
    for (PlayerCell cell : cells) {
      float radius = cell.getRadius();
      float cellX = cell.getX();
      float cellY = cell.getY();
      if (cellX - radius < minX) {
        minX = cellX - radius;
      }
      if (cellX + radius > maxX) {
        maxX = cellX + radius;
      }
      if (cellY - radius < minY) {
        minY = cellY - radius;
      }
      if (cellY + radius > maxY) {
        maxY = cellY + radius;
      }
    }
    x = (minX + maxX) / 2;
    y = (minY + maxY) / 2;
  }

  /**
   * Method to get the player size (as the sum of the size of all it cells)
   *
   * @return size
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Method to get the player radius ( sqrt(player.size/PI) )
   *
   * @return radius
   */
  @Override
  public float getRadius() {
    return radius;
  }
  
  /**
   * Method to update the player size and radius values
   */
  public void updateSize() {
    size = 0;
	for (PlayerCell cell : cells) {
	  size += cell.getSize();
	}
	radius = (float) Math.sqrt(getSize() / Math.PI);
  }

  /**
   * Method to get the list of a player's cells
   *
   * @return PlayerCell list
   */
  @Override
  public List<PlayerCell> getCells() {
    return cells;
  }

  /**
   * Method to add a PlayerCell to a Player
   *
   * @param cell : PlayerCell to add
   */
  @Override
  public void addCell(PlayerCell cell) {
    cells.add(cell);
  }

  /**
   * Method to remove a PlayerCell to a player
   *
   * @param cell : PlayerCell to remove
   * @return true if the PlayerCell is remove, false if not
   */
  @Override
  public boolean removeCell(PlayerCell cell) {
    return cells.remove(cell);
  }
}
