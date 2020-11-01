import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import metier.DataInfo;
import service.IPlayerRemote;

public class MapGraphics extends JPanel implements ActionListener {

	IPlayerRemote rm;
	private List<DataInfo> player_positions;
	private final int myID;
	Timer tm = new Timer(30,this);
	double x = 0;
	double y = 0;
	int centreX, centreY;
	int cstX = 0;
	int cstY = 0;
	JFrame jf;
	int i = 0;
	
	
	public MapGraphics(IPlayerRemote distant, int id)
	{
		player_positions = new ArrayList<>();
		rm = distant;
		myID = id;
		jf = new JFrame();
		jf.setTitle("Agario");
		jf.setSize(600,600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//++i;
		//g.fillOval(i, i, 20, 20);
		

		for(DataInfo v:player_positions)
		{
			//g.setColor(v.getTeam() == 0 ? Color.RED : Color.BLUE);
			if(v.getTeam() == 0)
				g.setColor(Color.RED);
			else if(v.getTeam() == 1)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.GREEN);
			g.fillOval((int)(v.getX()) + cstX -25, (int)(v.getY()) + cstY - 50, v.getSize(), v.getSize());
			
		}
		
		
		tm.start();
	}
	
	public void actionPerformed(ActionEvent e)
    {   
		
        Point point = MouseInfo.getPointerInfo().getLocation();
        Point pointJf = jf.getLocationOnScreen();
        System.out.println("pointJF: " + pointJf.getX() + " et " + pointJf.getY() );
        double dx = point.x - pointJf.getX() - x - cstX;
        double dy = point.y - pointJf.getY() -y - cstY;
        double length = Math.sqrt((dx*dx)+(dy*dy));
        if(length != 0)
        {
            dx = dx/length;
            dy = dy/length;
        }
        //System.out.println("Length : " + length);
        if(length > 5)
        {
            
            x += dx * 5;
            y += dy * 5;
            try {
                rm.Move(myID, x ,y);
                player_positions = rm.UpdateAllPositions();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else
        {
            try {
                player_positions = rm.UpdateAllPositions();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

		this.centreX  = jf.getWidth()/2;
		this.centreY = jf.getHeight()/2;
		cstX = centreX - (int)x;
		cstY = centreY - (int)y;
        repaint();
    }
	
}