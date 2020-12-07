package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import displayer.FoodDisplayer;
import displayer.HeaderHandlerDisplayer;
import displayer.OuterBoundsDisplayer;
import displayer.PlayerDisplayer;
import displayer.VictoryDisplayer;
import model.Player;
import model.PlayerCell;
import model.Board;
import model.Food;
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
	private float zoomRatio;

	public MapGraphics(IPlayerRemote distant, String username) throws RemoteException {		
		header = new HeaderHandler();
		zoomRatio = 1.6f;

		this.rm = distant;
		this.id = rm.registerPlayer(username);
		this.board = rm.getBoard();
		this.player = board.getPlayer(id);

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
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setTitle("Agar IO");
	}

	public void draw() {
		background(230);
		cursor(CROSS);

		try {
			boolean gameOver = rm.gameOver();
			this.board = rm.getBoard();
			this.player = this.board.getPlayer(this.id);
			double playerDiameter = this.player.getCell().getRadius()*2;
			zoomRatio = (float) (1 + (0.6f * (PlayerCell.CELL_MIN_SIZE*PlayerCell.CELL_MIN_SIZE)/(playerDiameter*playerDiameter)));

			pushMatrix();
			scale(zoomRatio);

			translate(
					(float)((centerX/zoomRatio) - player.getX()), 
					(float)((centerY/zoomRatio) - player.getY())
			);

			if (!gameOver) {
				actionPerformed();

				//this.zoomRatio = (float)(1+0.6 * (2500/(mySize*mySize)));

				for(Food food : this.board.getFoods()) {
					if(food.isAlive()) {
						FoodDisplayer.draw(food, this);		
					}
				}
				
				ArrayList<Player> p = new ArrayList<Player>(this.board.getPlayers());
				Collections.sort(p);
				
				for(Player player : p) {
					if(player.isAlive()) {						
						PlayerDisplayer.draw(player, this);
					}
				}

				OuterBoundsDisplayer.draw(
						this.board, 
						this.player.getX(), 
						this.player.getY(), 
						this
				);

				popMatrix();

				header.update(
						rm.getTimer(),
						this.board.getTeam(0).getScore(),
						this.board.getTeam(1).getScore()
						);
				HeaderHandlerDisplayer.draw(header, this);
			} else {
				VictoryDisplayer.draw(this.board.getWinners(), this);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}

	public void actionPerformed() throws RemoteException {
		rm.sendMousePosition(id, mouseX - (centerX) + player.getX(), mouseY - (centerY) + player.getY());
	}

}
