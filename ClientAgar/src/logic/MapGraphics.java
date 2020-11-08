package logic;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import metier.DataInfo;
import processing.core.PApplet;
import service.IPlayerRemote;

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
		try
		{
			x = rm.getPlayer(id).getX();
			y = rm.getPlayer(id).getY();
		}
		catch(Exception e)
		{
			
		}
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
		float zoomRatio = 1;
		double myX = 0,myY = 0;
		try {
			double mySize = rm.getPlayer(myID).getSize();
			myX = rm.getPlayer(myID).getX() + cstX;
			myY = rm.getPlayer(myID).getY() + cstY;
			double stage = (mySize/20);
			zoomRatio = (float) (1.04 - (stage*0.02));
			//System.out.println("size " + mySize);
			//System.out.println("ratio " + zoomRatio);
			int TID = rm.getPlayer(myID).getTeamID();
			if(TID == 0)
				fill(255,0,0);
			else
				fill(0,0,255);	
			circle((float)myX,(float)myY,(float)mySize*zoomRatio);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		for(DataInfo v:player_positions)
		{

			if(v.getTeam() == 0)
				fill(255, 0, 0);
			else if(v.getTeam() == 1)
				fill(0, 0, 255);
			else
			{
				fill((float)v.getR(),(float) v.getG(),(float)v.getB());
			}
			double objX = v.getX() + cstX;
			double objY = v.getY() + cstY;
			double offsetX = myX - objX; 
			double offsetY = myY - objY;
			objX += (1-zoomRatio) * offsetX;
			objY += (1-zoomRatio) * offsetY;
			
			circle((float)(objX), (float)(objY), (float) (v.getSize()*zoomRatio));	
		}
		
		try {
			header.update(rm.getTimer(), rm.getScore(0),rm.getScore(1));
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
            //System.out.println("X : " + x + " ; Y : " + y);
            try {
                rm.Move(myID, x ,y);
                player_positions = rm.UpdateAllPositions(myID);
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else
        {
            try {
                player_positions = rm.UpdateAllPositions(myID);
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