package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
		super.paintComponent(g);
		ArrayList<Player> players = control.getPlayers();
		System.out.println(players.size());
		Graphics2D g2d = (Graphics2D) g.create();
		if( players != null) {
			for( Player p: control.getPlayers()) {
				g2d.setColor(p.getColor());
				g2d.fillOval(p.getX(),p.getY(), 30, 30);
			}
		}
        g2d.dispose();
	}

}
