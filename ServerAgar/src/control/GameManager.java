package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import model.Board;
import model.CoordinateObject;
import model.Food;
import model.Player;
import model.PlayerCell;
import model.Team;
import remote.PlayerRemote;
import view.ServerGUI;

public class GameManager implements ActionListener {

	private PlayerRemote remoteManager;
	private PlayerManager playerManager;
	private Monitor monitor;
	private ServerGUI gui;

	private List<PlayerCell> movingObjects;

	private Timer tm;
	int gameTimer = 100000;

	private Board board;

	public GameManager() {
		board = new Board(500, 500, 300);
		board.addTeam(new Team(new Color(255, 0, 0), "Rouge", 50, 400));
		board.addTeam(new Team(new Color(0, 0, 255), "Bleu", 750, 400));

		movingObjects = new ArrayList<>();

		monitor = new Monitor(board);
		playerManager = new PlayerManager(monitor);

		tm = new Timer(16, this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!this.gameOver()) {
			applyMovePhysic();
			checkCollision();
		}
		if(gameTimer > 0) {
			gameTimer -= 16;
			if(gameTimer < 0) {
				gameTimer = 0;
			}
		}
		// Reset timer for next Tick
		tm.start();
	}

	public float getTimer() throws RemoteException {
		return gameTimer;
	}

	public boolean gameOver() {
		return gameTimer <= 0;
	}

	public void addPlayer(Player player) {
		board.addPlayer(player);
	}

	public void removePlayer(int id) {
		board.removePlayer(board.getPlayer(id));
	}

	public Board getBoard() {
		return board;
	}

	public void sendMousePosition(int id, float mouseX, float mouseY) {
		playerManager.sendMousePosition(board.getPlayer(id), mouseX, mouseY);
	}

	public boolean initServer(ServerGUI gui) {
		try {
			this.gui = gui;
			LocateRegistry.createRegistry(1099);
			this.remoteManager = new PlayerRemote(this);
			Naming.rebind("rmi://localhost:1099/PLM", remoteManager);
			tm.start();
		} catch (Exception e) {
			System.out.println("E01: Error initializing the server.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ServerGUI getGUI() {
		return this.gui;
	}

	private void checkCollision() {
		List<PlayerCell> cells = new ArrayList<>();
		for(Player player : board.getPlayers()) {
			if(player.isAlive()) {
				checkBoardCollision(player.getCell());
				checkFoodCollision(player.getCell());
				cells.add(player.getCell());
			}
		}

		for(int i = 0; i < cells.size() - 1; i++) {
			for(int j = i+1; j < cells.size(); j++) {
				if(cells.get(i).getPlayer().getTeam() == cells.get(j).getPlayer().getTeam()) {
					checkCellCollisionBetweenTeammates(cells.get(i), cells.get(j));					
				} else {
					checkCollisionBetweenEnnemies(cells.get(i), cells.get(j));
				}
			}
		}
	}

	private void checkBoardCollision(PlayerCell cell) {
		float radius = cell.getRadius();
		if(cell.getX() < radius) {
			cell.addRepulsionX((1-(cell.getX()/radius))*1.01f);
		}
		if(cell.getX() > board.getBoardWidth() - radius) {
			cell.addRepulsionX((((board.getBoardWidth() - cell.getX())/radius)-1)*1.01f);
		}
		if(cell.getY() < radius) {
			cell.addRepulsionY((1-(cell.getY()/radius))*1.01f);
		}
		if(cell.getY() > board.getBoardHeight() - radius) {
			cell.addRepulsionY((((board.getBoardHeight() - cell.getY())/radius)-1)*1.01f);
		}
	}

	private void checkCellCollisionBetweenTeammates(PlayerCell cellA, PlayerCell cellB){			
		float distX = cellA.getX() - cellB.getX();
		float distY = cellA.getY() - cellB.getY();
		float dist = (float)Math.hypot(distX, distY);
		float radiusA = cellA.getRadius();
		float radiusB = cellB.getRadius();
		if(dist < radiusA+radiusB) {				
			float superposition = radiusA+radiusB - dist;
			float proportionA = superposition/radiusA;
			float proportionB = superposition/radiusB;
			cellA.addRepulsionX(distX/dist*proportionA);
			cellA.addRepulsionY(distY/dist*proportionA);
			cellB.addRepulsionX(-distX/dist*proportionB);
			cellB.addRepulsionY(-distY/dist*proportionB);
		}
	}

	private void checkCollisionBetweenEnnemies(PlayerCell cellA, PlayerCell cellB) {
		PlayerCell bigger;
		PlayerCell smaller;
		if(cellA.getSize() > cellB.getSize()) {
			bigger = cellA;
			smaller = cellB;
		} else {
			bigger = cellB;
			smaller = cellA;
		}
		if(smaller.getSize() < bigger.getSize()*0.98) {
			double dist = Math.hypot(
				bigger.getX() - smaller.getX(),
				bigger.getY() - smaller.getY()
			);
			if(dist < bigger.getRadius()) {
				bigger.eat(smaller);
				playerManager.addScore(bigger.getPlayer().getTeam(), smaller.getSize());
				removePlayerCell(smaller);
			}
		}
	}

	private void checkFoodCollision(PlayerCell cell){
		for(Food food : board.getFoods()) {
			double dist = Math.hypot(
				cell.getX() - food.getX(),
				cell.getY() - food.getY()
			);
			if(dist < cell.getRadius()) {
				cell.eat(food);
				playerManager.addScore(
					cell.getPlayer().getTeam(),
					food.getSize()
				);
				this.removeFood(food);
			}
		}
	}

	private void applyMovePhysic() {
		List<CoordinateObject> toRemove = new ArrayList<>();
		for(CoordinateObject moveObj : movingObjects) {
			moveObj.applyMouvement();
			if(moveObj.getSpeedX() == 0 && moveObj.getSpeedY() == 0) {
				toRemove.add(moveObj);
			}
		}
		movingObjects.removeAll(toRemove);
		for(Player p : board.getPlayers()) {
			if(p.isAlive()) {
				p.getCell().applyMouvement();
			}
		}
	}

	private void removePlayerCell(PlayerCell cell) {
		Player player = cell.getPlayer();
		player.removeCell(cell);
		playerManager.addScore(player.getTeam(), -cell.getSize());
		if(player.getCells().size() <= 0) {
			playerManager.removePlayer(player);
		}
	}

	private void removeFood(Food food) {
		food.killFood();
	}

}
