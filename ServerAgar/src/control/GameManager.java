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
		board.addTeam(new Team(0, new Color(255, 0, 0), "Rouge", 50, 400));
		board.addTeam(new Team(1, new Color(0, 0, 255), "Bleu", 750, 400));
		
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
		gameTimer -= 16;
		// Reset timer for next Tick
		tm.start();
	}

	public float getTimer() throws RemoteException {
		return gameTimer <= 0 ? 0 : gameTimer;
	}

	public boolean gameOver() {
		return gameTimer <= 0;
	}
	
	public void addPlayer(Player player) {
		board.addPlayer(player);
	}
	
	public void removePlayer(int id) {
		board.remove(board.getPlayer(id));
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void sendMousePosition(int id, double mouseX, double mouseY) {
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
		double radius = cell.getRadius();
		if(cell.getX() < radius) {
			cell.addRepulsionX((1-(cell.getX()/radius))*1.01);
		}
		if(cell.getX() > board.getBoardWidth() - radius) {
			cell.addRepulsionX((((board.getBoardWidth() - cell.getX())/radius)-1)*1.01);
		}
		if(cell.getY() < radius) {
			cell.addRepulsionY((1-(cell.getY()/radius))*1.01);
		}
		if(cell.getY() > board.getBoardHeight() - radius) {
			cell.addRepulsionY((((board.getBoardHeight() - cell.getY())/radius)-1)*1.01);
		}
	}
	
	public void checkCellCollisionBetweenTeammates(PlayerCell cellA, PlayerCell cellB){			
		double distX = cellA.getX() - cellB.getX();
		double distY = cellA.getY() - cellB.getY();
		double dist = Math.hypot(distX, distY);
		double radiusA = cellA.getRadius();
		double radiusB = cellB.getRadius();
		if(dist < radiusA+radiusB) {				
			double superposition = radiusA+radiusB - dist;
			double proportionA = superposition/radiusA;
			double proportionB = superposition/radiusB;
			cellA.addRepulsionX(distX/dist*proportionA);
			cellA.addRepulsionY(distY/dist*proportionA);
			cellB.addRepulsionX(-distX/dist*proportionB);
			cellB.addRepulsionY(-distY/dist*proportionB);
		}
	}
	
	public void checkCollisionBetweenEnnemies(PlayerCell cellA, PlayerCell cellB) {
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
				removeObject(smaller);
			}
		}
	}
	
	public void checkFoodCollision(PlayerCell cell){
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
				this.removeObject(food);
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
	
	public void removeObject(CoordinateObject coordObject) {
		System.err.println("The server is not suppose tu remove a CoordinateObject who is not cast.");
		System.err.println("You need to verify all methodes 'removeObject()' of 'GameManager'.");
	}
	
	public void removeObject(PlayerCell cell) {
		Player player = cell.getPlayer();
		player.remove(cell);
		playerManager.addScore(player.getTeam(), -cell.getSize());
		if(player.getCells().size() <= 0) {
			playerManager.removePlayer(player);
		}
	}
	
	public void removeObject(Food food) {
		food.killFood();
	}
	
}
