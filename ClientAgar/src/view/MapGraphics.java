package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import displayer.CoordinateObjectDisplayer;
import displayer.HeaderHandlerDisplayer;
import displayer.OuterBoundsDisplayer;
import displayer.PlayerDisplayer;
import displayer.VictoryDisplayer;
import model.Player;
import model.CoordinateObject;
import processing.core.PApplet;
import remote.IPlayerRemote;

public class MapGraphics extends PApplet {

	private IPlayerRemote rm;
	private List<CoordinateObject> coordObjs;
	private final int id;
	private double x;
	private double y;
	private int centreX, centreY;
	private int cstX = 0;
	private int cstY = 0;
	private HeaderHandler header;

	public MapGraphics(IPlayerRemote distant, String username) throws RemoteException {
		coordObjs = new ArrayList<>();
		this.rm = distant;
		this.id = distant.registerPlayer(username);
		Runtime runtime = Runtime.getRuntime();
		Runnable runnable = () -> {
			try {
				rm.erasePlayer(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		runtime.addShutdownHook(new Thread(runnable));
		header = new HeaderHandler();
		x = rm.getPlayer(id).getX();
		y = rm.getPlayer(id).getY();
	}

	// method for setting the size of the window
	public void settings() {
		size(800, 800);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setTitle("Agar IO");
	}

	public void draw() {
		try {
			Player p = rm.getPlayer(id);
			float zoomRatio = 1;
			double mySize = p.getSize();
			background(230);
			cursor(CROSS);

			if (!rm.gameOver()) {
				actionPerformed();
				
				zoomRatio = (float)(1+0.6 * (2500/(mySize*mySize)));
				
				PlayerDisplayer.draw(p, zoomRatio, cstX, cstY, this);
				OuterBoundsDisplayer.draw(p.getX(), p.getY(), zoomRatio, cstX, cstY, this);
				
				for (CoordinateObject v : coordObjs) {
					CoordinateObjectDisplayer.draw(v, p, zoomRatio, cstX, cstY, this);
				}
				
				header.update(rm.getTimer(), rm.getScore(0), rm.getScore(1));
				HeaderHandlerDisplayer.draw(header, this);
			} else {
				VictoryDisplayer.draw(rm.getScore(0), rm.getScore(1), this);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}

	public void actionPerformed() throws RemoteException {
		if (focused && rm.getPlayer(id).isAlive()) {
			x = rm.getPlayer(id).getX();
			y = rm.getPlayer(id).getY();
			double dx = mouseX - x - cstX;
			double dy = mouseY - y - cstY;
			double length = Math.sqrt((dx * dx) + (dy * dy));
			if (length != 0) {
				dx = dx / length;
				dy = dy / length;
			}
			if (length > 10) {
				x += dx * 1;
				y += dy * 1;
				rm.move(id, x, y);
			}
			this.centreX = width / 2;
			this.centreY = height / 2;
			cstX = centreX - (int) x;
			cstY = centreY - (int) y;
		}
		coordObjs = rm.updateAllPositions(id);
	}

}
