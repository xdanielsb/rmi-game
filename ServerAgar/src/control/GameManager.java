package control;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import model.Food;
import model.SpaceObject;
import remote.PlayerRemoteImpl;
import view.ServerGUI;

public class GameManager {
	private List<Food> foods;
	private int nbFood;
	private PlayerRemoteImpl remoteManager;
	private ServerGUI gui;

	public GameManager() {
		this.nbFood = 150;
		foods = new ArrayList<>();
		for (int i = 0; i < nbFood; i++) {
			foods.add(new Food(-i-15000));
			foods.get(i);
		}
	}
	public List<SpaceObject> getFoods() {
		List<SpaceObject> availableFood = new ArrayList<>();
		for (Food f : foods) {
			if (f.isAlive())
				availableFood.add(f);
		}
		return availableFood;
	}

	public void removeFood(List<Integer> di) {
		for (Integer d : di) {
			for (Food f : foods) {
				if (d == f.getId()) {
					f.DisableFood();
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
