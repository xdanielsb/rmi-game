package model;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.Remote;

public interface Team extends Serializable, Remote {

	static final long serialVersionUID = 1L;

	public String getTeamName();

	public Color getColor();

	public int getScore();

}
