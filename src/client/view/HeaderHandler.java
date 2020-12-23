package client.view;

import java.util.List;

import interfaces.Team;

public class HeaderHandler {

	public float gameTimer;
	public List<Team> teams;

	public HeaderHandler() {
		gameTimer = 0;
	}

	/**
	 * Update the different values of the HeaderHandler
	 * 
	 * @param newTimer New value of the game timer
	 * @param teams    List of teams whose informations will be displayed
	 */
	public void update(float newTimer, List<Team> teams) {
		gameTimer = newTimer;
		this.teams = teams;
	}
}
