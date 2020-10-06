package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ViewMenu extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private JPanel panel;
	private JButton startGame;
	private JTextField nameField;
	private ControlClient control;
	
	public ViewMenu(ControlClient control) {
		this.control = control;
		setSize(500,100 );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, 
				    dim.height/2-this.getSize().height/2);
		
		this.panel = new JPanel();
		this.startGame = new JButton("Start Game");
		this.nameField = new JTextField("", 15);
		this.startGame.addActionListener(this);
		
		panel.setLayout( new FlowLayout());
		panel.add(this.nameField);
		panel.add(startGame);
		
		this.getContentPane().add( panel );
		this.getRootPane().setDefaultButton(startGame);
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

	public String getUserName() {	
		return this.nameField.getSelectedText()!=null ? this.nameField.getSelectedText(): "-";
	}



}
