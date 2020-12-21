package model;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Collection;
import java.util.List;

public interface Board extends Serializable, Remote {

	static final long serialVersionUID = 1L;

	public int getBoardWidth();

	public int getBoardHeight();

	public Player getPlayer(int id);

	public Collection<Player> getPlayers();

	public List<Team> getTeams();

	public List<Food> getFoods();

	public List<SpikeCell> getSpikeCells();

	public Team getWinners();

}
