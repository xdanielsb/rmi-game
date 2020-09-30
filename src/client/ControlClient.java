package client;

public class ControlClient {
	
	private ViewGame gameView;
	private ViewMenu menuView;
	
	public ControlClient() {
		this.menuView = new ViewMenu(this);
	}
	
	public void startGame() {
		this.gameView = new ViewGame(this);
	}

}
