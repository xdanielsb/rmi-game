public class HeaderHandler {

	private MapGraphics drawer;
	private float gameTimer;
	
	public HeaderHandler(MapGraphics drawer)
	{
		this.drawer = drawer;
		gameTimer = 0;
		
	}
	
	public void update(float newTimer)
	{
		gameTimer = newTimer;
	}
	
	public void draw()
	{
		drawer.textAlign(drawer.LEFT, drawer.TOP);
		drawer.fill(0);
		drawer.text("Timer : " + (int)gameTimer, 24, 0, 0); 
	}
}
