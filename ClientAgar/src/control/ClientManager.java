package control;

import java.rmi.Naming;

import processing.core.PApplet;
import remote.IPlayerRemote;
import view.MapGraphics;

public class ClientManager {
	private IPlayerRemote stub;
	public ClientManager() {
		
	}
	public void connectToServer(String username) {
		
		try {
			this.stub = (IPlayerRemote) Naming.lookup("rmi://localhost:1099/PLM");
			MapGraphics map = new MapGraphics(stub, username);
			PApplet.runSketch(new String[] { "MapGraphics" }, map);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
