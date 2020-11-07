package server;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	private JTextArea log;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	
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
		this.log = new JTextArea(20, 40);
		this.startGame = new JButton("Start Server");
		this.startGame.addActionListener(this);
		
		panel.setLayout( new FlowLayout());
		panel.add(startGame);
		panel.add(this.status);
		panel.add(this.log);
		
		this.getContentPane().add( panel );
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void addLog(String str) {
		this.log.setText(dtf.format(LocalDateTime.now()) + this.log.getText() + " "+ str + '\n');
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if( evt.getSource() == startGame) {
			this.startGame.setEnabled(false);
			boolean success = this.manager.initServer(this);
			this.status.setVisible(success);
			if(success) {
				this.addLog("Server initialized.");
			}else {
				this.addLog("Server could not start.");
			}
		}
	}

}