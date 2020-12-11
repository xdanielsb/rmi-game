package view;

import java.rmi.RemoteException;
import displayer.BoardDisplayer;
import displayer.HeaderHandlerDisplayer;
import displayer.VictoryDisplayer;
import model.Player;
import model.PlayerCell;
import model.Board;
import processing.core.PApplet;
import remote.IPlayerRemote;

public class MapGraphics extends PApplet {

	private IPlayerRemote rm;
	private Board board;
	private final int id;
	private float centerX;
	private float centerY;
	private HeaderHandler header;
	private Player player;
	private boolean gameOver;
	
	private boolean askForThrow;
	private boolean askForSplit;
	
	private BoardDisplayer boardDisplayer;

	public MapGraphics(IPlayerRemote distant, String username) throws RemoteException {
		header = new HeaderHandler();

		this.rm = distant;
		this.id = rm.registerPlayer(username);
		this.board = rm.getBoard();
		this.player = board.getPlayer(id);
		
		askForThrow = false;
		askForSplit = false;

		Runtime runtime = Runtime.getRuntime();
		Runnable runnable = () -> {
			try {
				rm.removePlayer(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		runtime.addShutdownHook(new Thread(runnable));
	}

	// method for setting the size of the window
	public void settings() {
		size(1280, 720);
		centerX = width/2;
		centerY = height/2;
		this.boardDisplayer = new BoardDisplayer(
				Math.min(width, height),
				0.1f,
				PlayerCell.CELL_MIN_SIZE,
				0.5f,
				20000
		);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setTitle("Agar IO");
	}

	public void draw() {
		background(230);
		cursor(CROSS);

		try {
			update();
			if (!this.gameOver) {

				boardDisplayer.draw(board, this.player, this.centerX, this.centerY, this);

				HeaderHandlerDisplayer.draw(this.header, this);
			} else {
				VictoryDisplayer.draw(this.board.getWinners(), this);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}

	public void update() throws RemoteException
	{		this.gameOver = rm.gameOver();
		
		try {			
			this.board = rm.getBoard();
		}catch(RemoteException e) {
			e.printStackTrace();
		}
		
		if(this.board.getPlayer(id).isAlive())
			this.player = this.board.getPlayer(this.id);
		
		this.header.update(
				rm.getTimer(),
				this.board.getTeams().get(0).getScore(),
				this.board.getTeams().get(1).getScore()
		);
		
		float modifyMouseX = mouseX - (centerX) + player.getX();
		float modifyMouseY = mouseY - (centerY) + player.getY();
		
		try {				
			rm.sendMousePosition(id, modifyMouseX, modifyMouseY);
		}catch(RemoteException e) {
			e.printStackTrace();
		}
		
		if(askForThrow) {
			try {
				rm.throwFood(this.id, modifyMouseX, modifyMouseY);
				askForThrow = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		if(askForSplit) {
			try {
				rm.split(this.id, modifyMouseX, modifyMouseY);
				askForSplit = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void mousePressed() {
		if(mouseButton == LEFT) {
			askForSplit = true;
		}else if(mouseButton == RIGHT) {
			askForThrow = true;
		}
	}

}
