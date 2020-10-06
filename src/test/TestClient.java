package test;

import java.util.ArrayList;

import client.ControlClient;
import client.Player;

public class TestClient {
	public static void main(String args[]) {
		
		System.out.println("hello");
		ControlClient ctrl1 = new ControlClient();
		
		
		int n = 2;
		ArrayList<Player> players = new ArrayList<>();
		
		for( int i= 0; i < n; i++) {
			players.add(new Player(ctrl1, "name"+i));
		}
		for( Player p: players) {
			p.start();
			ctrl1.addPlayer(p);
		}
	}
}
