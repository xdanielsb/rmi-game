package control;

public class Monitor {
	public final Object teamOne = new Object();
	public final Object teamTwo = new Object();
	public final Object addTeamate = new Object();
	public final Object countTeamate = new Object();

	private int scoreOne = 0;
	private int ScoreTwo = 0;
	private int nbTone = 0;
	private int nbTtwo = 0;

	public void addScoreOne(int amount) {
		synchronized (teamOne) {
			scoreOne += amount;
		}
	}

	public void addScoreTwo(int amount) {
		synchronized (teamTwo) {
			ScoreTwo += amount;
		}
	}
	
	public int getScoreOne() {
		return scoreOne;
	}

	public int getScoreTwo() {
		return ScoreTwo;
	}
	
	public void setTeamNumber(int i,int amount)
	{
		//already synchronized in registerPlayer mutex
		// no reason to synchro further at this point
		synchronized(addTeamate)
		{

			if(i == 0)
				nbTone += amount;
			else
				nbTtwo += amount;			
		}
		
		
		
	}
	
	public int getTeamNum(int t)
	{
		synchronized(addTeamate)
		{
			return t==0? nbTone:nbTtwo;
			
		}
	}

	

};
