package server;

import control.GameManager;

public class LauncherServer {

	public static void main(String[] args) {
		GameManager ga = new GameManager();
		ServerGUI gui = new ServerGUI(ga);
	}
}
