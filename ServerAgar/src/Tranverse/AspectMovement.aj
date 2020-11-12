package Tranverse;

public aspect AspectMovement {
	
	pointcut boundMovement(int i, double x, double y):within(metier.PlayerManager) && execution(public void Move(int,double,double)) && args(i,x,y);
	
	void around(int i,double x, double y):boundMovement(i,x,y)
	{
		double realX = x,realY = y;
		if(x < 0)
			realX = 0;
		else if(x > 1600)
			realX = 1600;
		if(y < 0)
			realY = 0;
		else if(y > 1600)
		{
			realY = 1600;
		}
		proceed(i,realX,realY);
	}
}
