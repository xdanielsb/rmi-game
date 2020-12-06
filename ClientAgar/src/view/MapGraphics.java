package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import displayer.CoordinateObjectDisplayer;
import displayer.HeaderHandlerDisplayer;
import displayer.OuterBoundsDisplayer;
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
			double myX = p.getX() + cstX;
			double myY = p.getY() + cstY;
			int TID = p.getTeam();
			float zoomRatio = 1;
			double mySize = p.getSize();
			background(230);
			cursor(CROSS);

			if (!rm.gameOver()) {
				actionPerformed();
				double stage = (mySize / 20);
				zoomRatio = (float) (1.04 - (stage * 0.02));
				if (TID == 0) fill(255, 0, 0);
				else fill(0, 0, 255);
				circle((float) myX, (float) myY, (float) mySize * zoomRatio);
				OuterBoundsDisplayer.draw(p.getX(), p.getY(), zoomRatio, cstX, cstY, this);
				for (CoordinateObject v : coordObjs) {
					fill(v.getColor().getRed(), v.getColor().getGreen(), v.getColor().getBlue());
					double objX = v.getX() + cstX;
					double objY = v.getY() + cstY;
					double offsetX = myX - objX;
					double offsetY = myY - objY;
					objX += (1 - zoomRatio) * offsetX;
					objY += (1 - zoomRatio) * offsetY;
					//float resizing = (1 - zoomRatio) * ((float) v.getSize() * zoomRatio);
					circle((float) (objX), (float) (objY), (float) (v.getSize() * zoomRatio));
				}
				header.update(rm.getTimer(), rm.getScore(0), rm.getScore(1));
				HeaderHandlerDisplayer.draw(header, this);
			} else {
				textSize(80);
				textAlign(CENTER);
				fill(0);
				text("Team " + (rm.getScore(0) >= rm.getScore(1) ? "red" : "blue") + " wins", 0, 200, 800, 100);
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
