package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import model.Player;
import model.SpaceObject;
import processing.core.PApplet;
import remote.IPlayerRemote;

public class MapGraphics extends PApplet {

	private IPlayerRemote rm;
	private List<SpaceObject> spaceobjs;
	private final int id;
	private double x;
	private double y;
	private int centreX, centreY;
	private int cstX = 0;
	private int cstY = 0;
	private HeaderHandler header;

	public MapGraphics(IPlayerRemote distant, String username) throws RemoteException {
		spaceobjs = new ArrayList<>();
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
		header = new HeaderHandler(this);
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
				drawOuterBounds(p.getX(), p.getY(), zoomRatio);
				for (SpaceObject v : spaceobjs) {
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
				header.draw();
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

	private void drawOuterBounds(double x, double y, float zoom) {
		noFill();
		strokeWeight(5000);
		stroke(180, 180, 180);
		float dx = (float) (cstX);
		float dy = (float) (cstY);
		float dxx = (float) (cstX);
		float dyy = (float) (cstY);
		float offsetX = (float) x + cstX - dx;
		float offsetY = (float) y + cstY - dy;
		float offsetXX = (float) x + cstX - dxx;
		float offsetYY = (float) y + cstY - dyy;
		dx += (1 - zoom) * offsetX;
		dy += (1 - zoom) * offsetY;
		dxx += (1 - zoom) * offsetXX;
		dyy += (1 - zoom) * offsetYY;

		float resizing = (1 - zoom) * (1600);

		rect(dx - 2500, dy - 2500, 6600 - resizing, 6600 - resizing);
		stroke(255, 0, 0);
		strokeWeight(1);
		rect(dxx, dyy, 1600 - resizing, 1600 - resizing);
		stroke(0, 0, 0);

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
		spaceobjs = rm.updateAllPositions(id);
	}

}
