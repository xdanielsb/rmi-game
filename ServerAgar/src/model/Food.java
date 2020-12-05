package model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Food extends CoordinateObject implements ActionListener {

	Timer tm = new Timer(10000, this);
	private boolean isAlive;

	public Food(int id) {
		super((Math.random() * 1500) + 50, (Math.random() * 1500) + 50, 20);
		this.setColor(new Color((int)(Math.random() * 0x1000000)).brighter());
		this.isAlive = true;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void DisableFood() {
		this.setAlive(false);
		tm.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setAlive(true);
	}

}
