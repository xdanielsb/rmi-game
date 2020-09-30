package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel{
	/**
	 * 
	 */
	private JPanel panel;
	private ControlClient control;
	
	public Canvas(ControlClient control) {
		this.control = control;
		setSize(500,300 );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);
		setVisible(true);
	}

	public void paintComponent(Graphics g){
		ArrayList<Player> players = control.getPlayers();
		if( players != null) {
			for( Player p: control.getPlayers()) {
				g.fillOval(p.getX(),p.getY(), 30, 30);
		        g.setColor(Color.red);  
			}
		}
	}

}
