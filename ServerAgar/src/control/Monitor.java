package control;

public class Monitor {
	
	public final Object[] teamBell;
	public final Object addTeamate;
	public final int nbTeam = 2;

	private int[] score;
	private int[] nbPlayerTeam;
	
	public Monitor() {
		teamBell = new Object[nbTeam];
		for(int i = 0; i < teamBell.length; i++) {
			teamBell[i] = new Object();
		}
		addTeamate = new Object();
		
		score = new int[nbTeam];
		nbPlayerTeam = new int[nbTeam];
	}

	public void addScore(int team, int amount) {
		if(team < 0 || team >= nbTeam) {
			return;
		}
		synchronized (teamBell[team]) {
			score[team] += amount;
		}
	}
	
	public int getScore(int team) {
		return score[team];
	}
	
	public void addTeamNumber(int team,int amount) {
		
		//already synchronized in registerPlayer mutex
		// no reason to synchro further at this point
		if(team < 0 && team >= nbTeam) {
			return;
		}
		synchronized(addTeamate) {
			nbPlayerTeam[team] += amount;
		}
		
	}
	
	public int getTeamNum(int team) {
		if(team < 0 || team >= nbTeam) {
			return 0;
		}
		synchronized(addTeamate) {
			return nbPlayerTeam[team];
		}
	}

	

};
