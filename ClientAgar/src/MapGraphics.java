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

import processing.core.PApplet;

public class MapGraphics extends PApplet {

	IPlayerRemote rm;
	private List<DataInfo> player_positions;
	private final int myID;
	double x = 0;
	double y = 0;
	int centreX, centreY;
	int cstX = 0;
	int cstY = 0;
	int i = 0;
	HeaderHandler header;
	
	
	public MapGraphics(IPlayerRemote distant, int id)
	{
		player_positions = new ArrayList<>();
		rm = distant;
		myID = id;
		header = new HeaderHandler(this);
	}
	
	// method for setting the size of the window
    public void settings(){
        size(800, 800);
    }

    // identical use to setup in Processing IDE except for size()
    public void setup()
    {
    	surface.setTitle("Agar IO");
    }
	
	public void draw()
	{

        background(255);
		actionPerformed();
		
		for(DataInfo v:player_positions)
		{
			//g.setColor(v.getTeam() == 0 ? Color.RED : Color.BLUE);

			if(v.getTeam() == 0)
				fill(255, 0, 0);
			else if(v.getTeam() == 1)
				fill(0, 0, 255);
			else
				fill(0, 255, 0);
			circle((float)(v.getX() + cstX), (float)(v.getY() + cstY), (float)v.getSize());	
		}
		
		try {
			header.update(rm.getTimer());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		header.draw();
		
	}
	
	public void actionPerformed()
    {   
		
        double dx = mouseX - x - cstX;
        double dy = mouseY - y - cstY;
        double length = Math.sqrt((dx*dx)+(dy*dy));
        if(length != 0)
        {
            dx = dx/length;
            dy = dy/length;
        }
        //System.out.println("Length : " + length);
        if(length > 5)
        {
            
            x += dx * 1;
            y += dy * 1;
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

		this.centreX  = width/2;
		this.centreY = height/2;
		cstX = centreX - (int)x;
		cstY = centreY - (int)y;
    }
	
}