package client.control;

import client.view.MapGraphics;
import interfaces.IPlayerRemote;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import processing.core.PApplet;

public class ClientManager {
  private IPlayerRemote stub;
  private static final String serverUrl = "rmi://localhost:1099/PLM";

  public ClientManager() {}

  /**
   * Connect the client to the server
   *
   * @param username Username of the player
   */
  public void connectToServer(String username) {
    try {
      this.stub = (IPlayerRemote) Naming.lookup(serverUrl);
      MapGraphics map = new MapGraphics(stub, username);
      PApplet.runSketch(new String[] {"MapGraphics"}, map);
    } catch (RemoteException | MalformedURLException | NotBoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Check if there is connectivity user client and server
   *
   * @return true if the server is online
   */
  public boolean isServerOnline() {
    try {
      Naming.lookup(serverUrl);
    } catch (RemoteException | MalformedURLException | NotBoundException e) {
      return false;
    }
    return true;
  }
}
