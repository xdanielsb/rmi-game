package client;

import java.util.ArrayList;

public class ControlClient {
	
	private ViewGame gameView;
	private ViewMenu menuView;
	private ArrayList<Player> players = new ArrayList<>();
	private Player player;
	
	public ControlClient() {
		this.menuView = new ViewMenu(this);
	}
	
	public void startGame() {
		this.gameView = new ViewGame(this);
		//player = new Player(this, this.menuView.getUserName());
		//player.start();
		//this.addPlayer(player);
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public void addPlayer(Player player) {
		// DEBUG: this is for testing the client side
		// this list is a remote object
		this.players.add(player);
	}
	public Player getCurrentPlayer() {
		return this.player;
	}

	public ArrayList<Player>  getPlayers() {
		return this.players;
	}

	public ViewGame getWindow() {
		return gameView;
	}

}
