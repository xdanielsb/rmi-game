package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import service.PlayerRemoteImpl;

public class serverRMI {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			PlayerRemoteImpl od = new PlayerRemoteImpl();
			Naming.rebind("rmi://localhost:1099/PLM", od);
			System.out.println("Server is ready to go.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
