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
	private List<DataInfo> player_info;
	private final int myID;
	Timer tm = new Timer(30,this);
	double x = 0;
	double y = 0;
	//int x = 0;
	//int y = 0;
	
	
	
	public MapGraphics(IPlayerRemote distant, int id)
	{
		player_info = new ArrayList<>();
		rm = distant;
		myID = id;
		JFrame jf = new JFrame();
		jf.setTitle("Agario");
		jf.setSize(400,400);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(DataInfo v:player_info)
		{
			
			//g.setColor(v.getTeam() == 0? Color.RED:Color.BLUE);
			if(v.getTeam() == 0)
				g.setColor(Color.RED);
			else if(v.getTeam() == 1)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.GREEN);
			g.fillOval((int)v.getX(), (int)v.getY(), v.getSize(), v.getSize());
		}
		
		
		tm.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Point point = MouseInfo.getPointerInfo().getLocation();
		double dx = point.x - x-25;
		double dy = point.y -y-50;
		double length = Math.sqrt((dx*dx)+(dy*dy));
		if(length != 0)
		{
			dx = dx/length;
			dy = dy/length;
		}
		
		if(length > 2)
		{
			
			x += dx * 2;
			y += dy * 2;
			try {
				rm.Move(myID, x ,y);
				player_info = rm.UpdateAllPositions();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			try {
				player_info = rm.UpdateAllPositions();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		repaint();
	}
	
}
