package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Food implements ActionListener {
	private DataInfo position;
	private boolean isAlive;
	Timer tm = new Timer(10000, this);
	
	public Food(double xPos, double yPos) {
		position = new DataInfo(xPos, yPos, 20, 2);
		isAlive = true;
	}


	public void DisableFood() {
		isAlive = false;
		tm.start();
	}

	public boolean isAlive() {
		return isAlive;
	}

	public DataInfo getPosition() {
		return position;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		isAlive = true;
	}

}
