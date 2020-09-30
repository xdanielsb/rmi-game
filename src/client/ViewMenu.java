package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ViewMenu extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private JPanel panel;
	private JButton startGame;
	private ControlClient control;
	
	public ViewMenu(ControlClient control) {
		this.control = control;
		setSize(500,300 );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);
		
		this.panel = new JPanel();
		this.startGame = new JButton("Start Game");
		this.startGame.addActionListener(this);
		
		panel.setLayout( new FlowLayout());
		panel.add(startGame);
		
		this.getContentPane().add( panel );
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if( evt.getSource() == startGame) {
			this.startGame.setEnabled(false);
			this.control.startGame();
		}
	}

}
