package client;
/**
 * Frame of the Graphic User Intarface (GUI)
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class ViewGame extends JFrame implements MouseMotionListener {
	
	private ControlClient control;
	private Player player;
	public final int WIDTH = 500;
	public final int HEIGHT = 500;
	
	public ViewGame(ControlClient control) {
		this.control = control;
		Canvas cv = new Canvas(control);
		setSize(this.WIDTH, this.HEIGHT);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);

		this.getContentPane().add( cv );
		this.addMouseMotionListener(this);
		setVisible( true );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//this.control.getCurrentPlayer().setXY(e.getX(), e.getY());
	}

}
