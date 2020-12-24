package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface Player extends Serializable, Remote {

  static final long serialVersionUID = 1L;

  public int getId();

  public String getName();

  public boolean isAlive();

  public void setAlive(boolean alive);

  public float getThrowDirectionX();

  public float getThrowDirectionY();

  public void setThrowDirection(float throwDirectionX, float throwDirectionY);

  public float getSplitDirectionX();

  public float getSplitDirectionY();

  public void setSplitDirection(float splitDirectionX, float splitDirectionY);

  public float getX();

  public float getY();

  public void updateCoordinates();

  public int getSize();

  public float getRadius();

  public List<PlayerCell> getCells();

  public Team getTeam();

  public void setTeam(Team team);

  public void addCell(PlayerCell cell);

  public boolean removeCell(PlayerCell cell);
}
