package client.view;

import client.control.ClientManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ClientConnectGUI extends JFrame implements ActionListener {

  private JPanel panel;
  private JLabel lblusername;
  private JLabel status;
  private JButton startGame;
  private ClientManager manager;
  private JTextField username;

  public ClientConnectGUI(ClientManager manager) {
    this.manager = manager;
    setSize(300, 200);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(
        dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

    this.panel = new JPanel();
    this.lblusername = new JLabel("Username", SwingConstants.CENTER);
    this.status = new JLabel("", SwingConstants.CENTER);

    this.username = new JTextField(15);
    this.startGame = new JButton("Connect to Server");
    this.startGame.addActionListener(this);

    panel.setLayout(new FlowLayout());

    panel.add(this.lblusername);
    panel.add(this.username);
    panel.add(this.startGame);
    panel.add(this.status);

    this.getContentPane().add(panel);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * Triggered when we click on a Swing component with an action listener.
   *
   * @param evt Event triggered by the element
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == startGame) {
      if (this.manager.isServerOnline()) {
        this.startGame.setEnabled(false);
        this.manager.connectToServer(this.getUserName());
        this.dispose();
      } else {
        this.setStatus("Cannot connect to the server.");
      }
    }
  }

  /**
   * Returns the username written by the user in the client GUI
   *
   * @return String The username written in the field
   */
  public String getUserName() {
    return this.username.getText();
  }

  /**
   * Display response after trying to make connection
   *
   * @param status
   */
  public void setStatus(String status) {
    this.status.setText(status);
  }
}
