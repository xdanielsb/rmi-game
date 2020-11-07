package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import metier.GameManager;
import service.PlayerRemoteImpl;

public class Launcher {

	public static void main(String[] args) {
		GameManager ga = new GameManager();
		ServerGUI gui = new ServerGUI(ga);
	}
}
