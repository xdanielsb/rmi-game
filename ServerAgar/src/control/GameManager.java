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
	private ServerGUI gui;
	
	private List<PlayerCell> movingObjects;
	
	private Timer tm;
	int gameTimer = 40000000;
	
	private Board board;

	public GameManager() {
		board = new Board(500, 500, 300);
		board.addTeam(new Team(0, new Color(255, 0, 0), "Rouge", 50, 400));
		board.addTeam(new Team(1, new Color(0, 0, 255), "Bleu", 750, 400));
		
		movingObjects = new ArrayList<>();
		
		playerManager = new PlayerManager(board);
		
		tm = new Timer(16, this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!this.gameOver()) {
			applyMovePhysic();
			checkCollision();
			checkFoodCollision();
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
		board.removePlayer(board.getPlayer(id));
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
		for(Player player : board.getPlayers()) {
			checkBoardCollision(player.getCell());
//			checkCellCollision(player.getCell());
//			checkFoodCollision(player.getCell());
		}
		List<Player> team1, team2;
		team1 = board.getTeam(0).getPlayers();
		team2 = board.getTeam(1).getPlayers();
		for( Player pteam1: team1) {
			if(!pteam1.isAlive()) continue;
			for(Player pteam2: team2) {
				if(!pteam2.isAlive()) continue;
				if( (pteam1.dist(pteam2) < Math.max(pteam1.getCell().getRadius()/2, pteam2.getCell().getRadius()/2)) && Math.abs(pteam1.getCell().getRadius()-pteam2.getCell().getRadius()) >= 10) {
					if (pteam1.getCell().getRadius() < pteam2.getCell().getRadius()) {
						//pteam1.setAlive(false);
						pteam2.setSize(Math.sqrt((pteam2.getCell().getRadius()) * (pteam2.getCell().getRadius())
								+ (pteam1.getCell().getRadius()) * (pteam1.getCell().getRadius())) * 2);
						updateScore(pteam2, (int) pteam2.getSize());
						playerManager.removePlayer(pteam1);
						break;
					}else {
						//pteam2.setAlive(false);
						pteam1.setSize(Math.sqrt((pteam2.getCell().getRadius()) * (pteam2.getCell().getRadius())
								+ (pteam1.getCell().getRadius()) * (pteam1.getCell().getRadius())) * 2);
						updateScore(pteam1, (int) pteam1.getSize());
						playerManager.removePlayer(pteam2);
					}
				}
			}
		}
	}
	
	private void checkBoardCollision(PlayerCell cell) {
		double radius = cell.getRadius();
		if(cell.getX() < radius) {
			cell.addRepulsionX((1-(cell.getX()/radius))*1.01);
			System.out.println((1-(cell.getX()/radius))*1.01);
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
	
//	public void checkCellCollision(PlayerCell cell){
//		
//	}
	
//	public void checkFoodCollision(PlayerCell cell){
//		
//	}
	
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
			p.getCell().applyMouvement();
		}
	}

	private void updateScore(Player p, int amount) {
		p.getTeam().addToScore(amount);
	}

	private void checkFoodCollision() {
		List<Food> eatenFood = new ArrayList<>();
		for (Player p : board.getPlayers()) {
			double size = p.getCell().getRadius();
			for (Food f : board.getFoods()) {
				if(f.isAlive()) {
					double dx = p.getX() - f.getX();
					double dy = p.getY() - f.getY();
					double length = Math.sqrt((dx * dx) + (dy * dy));
					if (length < size) {
						p.setSize(p.getSize()+f.getSize());
						updateScore(p, (int) f.getSize());
						eatenFood.add(f);						
					}					
				}
			}
			board.removeFood(eatenFood);
			eatenFood.clear();
		}
	}
	
}
