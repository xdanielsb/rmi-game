import java.rmi.Naming;


import processing.core.PApplet;
import service.IPlayerRemote;


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
