package model;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface Player extends Serializable, Remote {

	static final long serialVersionUID = 1L;

	public String getName();

	public boolean isAlive();

	public float getX();

	public float getY();

	public int getSize();

	public float getRadius();

	public List<PlayerCell> getCells();

}
