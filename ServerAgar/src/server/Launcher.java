package server;

import metier.GameManager;

public class Launcher {

	public static void main(String[] args) {
		GameManager ga = new GameManager();
		ServerGUI gui = new ServerGUI(ga);
	}
}
