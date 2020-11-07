package logic;
import java.rmi.Naming;

import processing.core.PApplet;
import service.IPlayerRemote;

public class LauncherClient {

	public static void main(String[] args) {
		ClientManager manager = new ClientManager();
		ClientConnectGUI cnt = new ClientConnectGUI(manager);

	}

}
