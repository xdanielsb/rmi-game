package client;
/**
 * Frame of the Graphic User Intarface (GUI)
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class ViewGame extends JFrame implements KeyListener{
	
	private ControlClient control;
	private Player player;
	
	public ViewGame(ControlClient control) {
		this.control = control;
		Canvas cv = new Canvas(control);
		setSize(500, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);

		this.getContentPane().add( cv );
		setVisible( true );
		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			this.control.getCurrentPlayer().move(1, 0);
		}else if(e.getKeyCode()== KeyEvent.VK_LEFT) {
			this.control.getCurrentPlayer().move(-1, 0);
        }else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
        	this.control.getCurrentPlayer().move(0, -1);
        }else if(e.getKeyCode()== KeyEvent.VK_UP) {
        	this.control.getCurrentPlayer().move(0, 1);
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
