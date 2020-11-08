package metier;

public class Monitor {
	public final Object teamOne = new Object();
	public final Object teamTwo = new Object();
	
	private int scoreOne = 0;
	private int ScoreTwo = 0;
	
	public void addScoreOne(int amount)
	{
		synchronized(teamOne)
		{
			scoreOne += amount;
			teamOne.notifyAll();
		}
	}
	
	public void addScoreTwo(int amount)
	{
		synchronized (teamTwo) {
			ScoreTwo += amount;
			teamTwo.notifyAll();
			
		}
	}
	
	public int getScoreOne()
	{
		return scoreOne;
	}
	public int getScoreTwo()
	{
		return ScoreTwo;
	}

};
