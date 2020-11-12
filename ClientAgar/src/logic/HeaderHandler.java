package logic;

public class HeaderHandler {

	private MapGraphics drawer;
	private float gameTimer;
	private int xP;
	private int yP;

	public HeaderHandler(MapGraphics drawer) {
		this.drawer = drawer;
		gameTimer = 0;
	}

	public void update(float newTimer, int so, int st) {
		gameTimer = newTimer;
		xP = so;
		yP = st;
	}

	public void draw() {
		drawer.textAlign(drawer.CENTER);
		drawer.fill(0);
		drawer.textSize(24);
		drawer.text("Timer : " + (int) gameTimer, 0, 30, 800, 100);
		drawer.fill(255, 0, 0);
		drawer.textSize(20);
		drawer.text("Score " + xP, 0, 30, 400, 100);
		drawer.fill(0, 0, 255);
		drawer.textSize(20);
		drawer.text("Score " + yP, 400, 30, 400, 100);

	}
}
