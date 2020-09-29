package client;

public class GUI implements Runnable{
	
	private Player p;
	
	GUI(){
		Server s = Server.getConnection();
		p = s.getPlayer();
	}
	
	@Override
	public void run() {
		//TODO, meanwhile the playes is playning
		while(true) {
			// listen the keyboards
			int dir = 0; 
			p.move(dir);
			
		}
	}
	
}
