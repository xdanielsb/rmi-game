package server;


import server.control.GameManager;
import server.view.ServerGUI;

public class LauncherServer {
	public static void main(String[] args) {
		GameManager ga = new GameManager();
		new ServerGUI(ga);
	}
}
