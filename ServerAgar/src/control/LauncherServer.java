package control;

import view.ServerGUI;

public class LauncherServer {

	public static void main(String[] args) {
		GameManager ga = new GameManager();
		ServerGUI gui = new ServerGUI(ga);
	}
}
