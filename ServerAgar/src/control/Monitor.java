package control;

import model.Board;

public class Monitor {
	
	public final Object[] teamBell;
	public final Object addTeamate;
	
	private Board board;
	
	public Monitor(Board board) {
		this.board = board;
		teamBell = new Object[board.getNbTeam()];
		for(int i = 0; i < teamBell.length; i++) {
			teamBell[i] = new Object();
		}
		addTeamate = new Object();
	}

	public void addScore(int team, int amount) {
		if(team < 0 || team >= board.getNbTeam()) {
			return;
		}
		synchronized (teamBell[team]) {
			board.getTeam(team).addToScore(amount);
		}
	}

};
