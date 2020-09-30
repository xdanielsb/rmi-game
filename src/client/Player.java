package client;

public class Player extends Thread{
	private int x;
	private int y;
	private ControlClient control;
	public Player(ControlClient control) {
		this.control = control;
	}
	
	public void move( int dx, int dy) {
		this.x += dx;
		this.y += dy;
		System.out.println(this.x + " " + this.y);
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	@Override
	public void run() {
		while(true) {
			this.control.getWindow().repaint();
			System.out.println("hello");
			// send player data
			// receive players data
		}
	};
}
