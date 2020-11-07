package server;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.GameManager;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private JPanel panel;
	private JLabel status;
	private JButton startGame;
	private GameManager manager;
	
	public ServerGUI(GameManager manager) {
		this.manager  = manager;
		setSize(500,300 );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);
		
		this.panel = new JPanel();
		this.status = new JLabel("Server Running !", JLabel.CENTER);
		this.status.setForeground(Color.green);
		this.status.setVisible(false);
		this.startGame = new JButton("Start Server");
		this.startGame.addActionListener(this);
		
		panel.setLayout( new FlowLayout());
		panel.add(startGame);
		panel.add(this.status);
		
		this.getContentPane().add( panel );
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if( evt.getSource() == startGame) {
			this.startGame.setEnabled(false);
			boolean success = this.manager.initServer();
			this.status.setVisible(success);
		}
	}

}