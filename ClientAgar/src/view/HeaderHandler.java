package view;

public class HeaderHandler {

	public float gameTimer;
	public int xP;
	public int yP;

	public HeaderHandler() {
		gameTimer = 0;
	}

	public void update(float newTimer, int so, int st) {
		gameTimer = newTimer;
		xP = so;
		yP = st;
	}
}
