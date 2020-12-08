package control;

import model.Board;
import model.Team;

public class Monitor {
	
	public final Object[] teamBell;
	public final Object addTeamate;
		
	public Monitor(Board board) {
		teamBell = new Object[board.getNbTeam()];
		for(int i = 0; i < teamBell.length; i++) {
			teamBell[i] = new Object();
		}
		addTeamate = new Object();
	}

	public void addScore(Team team, int amount) {
		if(team == null) {
			return;
		}
		synchronized (teamBell[team.getId()]) {
			team.addToScore(amount);
		}
	}

};
