package metier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class Food implements ActionListener {
	private DataInfo position;
	private boolean isAlive;
	Timer tm = new Timer(10000, this);
	private double R,G,B;

	public Food(double xPos, double yPos) {
		PickColor();
		position = new DataInfo(xPos, yPos, 20, 2,R,G,B);
		isAlive = true;
	}
	
	private void PickColor()
	{
		double dominantChoice = Math.random();
		if(dominantChoice < 0.33)
		{
			R = Math.random() * 75 + 180;
			double subDom = Math.random();
			if(subDom < 0.5)
			{
				G = Math.random() * 255;
				B = Math.random() * 40;
			}
			else
			{
				G = Math.random() * 40;
				B = Math.random() * 255;
			}
		}
		else if(dominantChoice < 0.67)
		{
			G = Math.random() * 75 + 180;
			double subDom = Math.random();
			if(subDom < 0.5)
			{
				R = Math.random() * 255;
				B = Math.random() * 40;
			}
			else
			{
				R = Math.random() * 40;
				B = Math.random() * 255;
			}
		}
		else
		{
			B = Math.random() * 75 + 180;
			double subDom = Math.random();
			if(subDom < 0.5)
			{
				R = Math.random() * 255;
				G = Math.random() * 40;
			}
			else
			{
				R = Math.random() * 40;
				G = Math.random() * 255;
			}
		}
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
