package control;

import view.ClientConnectGUI;

public class LauncherClient {

	public static void main(String[] args) {
		ClientManager manager = new ClientManager();
		new ClientConnectGUI(manager);
	}

}
