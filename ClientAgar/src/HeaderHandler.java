public class HeaderHandler {

	private MapGraphics drawer;
	private float gameTimer;
	private double xP;
	private double yP;
	
	
	public HeaderHandler(MapGraphics drawer)
	{
		this.drawer = drawer;
		gameTimer = 0;
		
	}
	
	public void update(float newTimer, double x, double y)
	{
		gameTimer = newTimer;
		xP = x;
		yP = y;
	}
	
	public void draw()
	{
		drawer.textAlign(drawer.LEFT, drawer.TOP);
		drawer.fill(0);
		drawer.text("Timer : " + (int)gameTimer + ", X : " + xP + " ; Y : " + yP, 24, 0, 0); 
	}
}
