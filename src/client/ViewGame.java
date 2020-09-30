package client;
/**
 * Frame of the Graphic User Intarface (GUI)
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class ViewGame extends JFrame {
	
	private ControlClient control;
	
	public ViewGame(ControlClient control) {
		this.control = control;
		Canvas cv = new Canvas(control);
		setSize(500, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);

		this.getContentPane().add( cv );
		setVisible( true );
	}
}
