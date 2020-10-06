package client;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Player extends Thread{
	private int x;
	private int y;
	private int speed;
	private String name;
	private Color color;
	private ControlClient control;
	public Player(ControlClient control, String name) {
		this.control = control;
		this.speed = 10;
		this.x = (int) Math.abs(Math.random() * 400 + 10);
		this.y = (int) Math.abs(Math.random() * 400 + 10);
		this.name = name;
		this.color = this.randomColor();
	}
	
	public void move( int dx, int dy) {
		this.x =  Math.max(0,Math.min(dx+this.x, control.getWindow().WIDTH));
		this.y = Math.max(0,Math.min(dy+this.y, control.getWindow().HEIGHT));
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getSpeed() {
		return this.speed;
	}
	
	public Color randomColor() {
        int red = (int) Math.abs(Math.random() * 255);
        int green = (int) Math.abs(Math.random() * 255);
        int blue = (int) Math.abs(Math.random() * 255);
        return new Color(red, green, blue);
    }
	
	public int getRandom( int a , int b) {
		return (int) ((int) a + (Math.random() * (b+1 - a)));
	}
	
	@Override
	public void run() {
		this.control.startGame();
		while(true) {
			this.control.getWindow().repaint();
			try {
                sleep(1000/60);
                this.move(this.getRandom(-2, 2), this.getRandom(-2, 2));
            } catch (InterruptedException ex) {
            }
			// send player data
			// receive players data
		}
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return this.color;
	};
}
