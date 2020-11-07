package logic;

import java.rmi.Naming;

import processing.core.PApplet;
import service.IPlayerRemote;

public class ClientManager {
	private ClientConnectGUI clientConnect;
	private IPlayerRemote stub;
	
	public ClientManager() {
		
	}

	public void connectToServer(ClientConnectGUI clientConnect) {
		this.clientConnect = clientConnect;
		try {
			this.stub = (IPlayerRemote) Naming.lookup("rmi://localhost:1099/PLM");
			MapGraphics map = new MapGraphics(stub, stub.registerPlayer(clientConnect.getUserName()));
			PApplet.runSketch(new String[] { "MapGraphics" }, map);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
