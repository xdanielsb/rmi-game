package control;

import model.Board;
import model.Team;

public class Monitor {

	public final Object addTeamate;

	public Monitor(Board board) {
		addTeamate = new Object();
	}

	public void addScore(Team team, int amount) {
		if(team == null) {
			return;
		}
		synchronized (team.getBell()) {
			team.addToScore(amount);
		}
	}

};
