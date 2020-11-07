import java.awt.MouseInfo;
import java.awt.Point;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import service.IPlayerRemote;

import processing.core.PApplet;

public class ClientRMI {

	public static void main(String[] args) {
		
		IPlayerRemote stub;
		try {
			stub = (IPlayerRemote)Naming.lookup("rmi://localhost:1099/PLM");
			MapGraphics map = new MapGraphics(stub,stub.registerPlayer("Bobby"));
			PApplet.runSketch(new String[] {"MapGraphics"}, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
