package tranverse;

import model.Player;

public aspect aspectMovement {

	pointcut boundMovement(Player p, double x, double y):within(control.PlayerManager) && execution(public void move(Player,double,double)) && args(p,x,y);

	void around(Player p, double x, double y):boundMovement(p,x,y)
	{
		double realX = x, realY = y;
		if (x < 0)
			realX = 0;
		else if (x > 1500)
			realX = 1500;
		if (y < 0)
			realY = 0;
		else if (y > 1500) {
			realY = 1500;
		}
		proceed(p, realX, realY);
	}
}
