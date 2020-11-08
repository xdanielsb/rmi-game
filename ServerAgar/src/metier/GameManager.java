package metier;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

import server.ServerGUI;
import service.PlayerRemoteImpl;

public class GameManager {
	// private List<DataInfo> foods;
	private List<Food> foods;
	private int nbFood;
	private PlayerRemoteImpl remoteManager;
	private ServerGUI gui;
	
	public GameManager() {
		this.nbFood  = 30;
		foods = new ArrayList<>();
		for (int i = 0; i < nbFood; i++) {
			foods.add(new Food((Math.random() * 650) + 50, (Math.random() * 700) + 50));
		}

	}

	public List<DataInfo> GetFoods() {
		List<DataInfo> availableFood = new ArrayList<>();
		for (Food f : foods) {
			if (f.isAlive())
				availableFood.add(f.getPosition());
		}
		return availableFood;
	}

	public void RemoveFood(List<DataInfo> di) {
		for (DataInfo d : di) {
			for (Food f : foods) {
				if (d == f.getPosition()) {
					f.DisableFood();
					break;
				}
			}
		}
	}

	
	public boolean initServer(ServerGUI gui) {
		try {
			this.gui = gui;
			LocateRegistry.createRegistry(1099);
			this.remoteManager = new PlayerRemoteImpl(this);
			Naming.rebind("rmi://localhost:1099/PLM", remoteManager);
			System.out.println("L01: Server is ready to go.");
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

}
