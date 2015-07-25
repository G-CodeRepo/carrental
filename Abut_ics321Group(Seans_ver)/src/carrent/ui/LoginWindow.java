package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import carrent.db.DBInterface;
import carrent.entity.Account;

public class LoginWindow extends JPanel{

	private JTextField userName;
	private JPasswordField password;
	private JButton confirm, cancel;
	
	private LoginWindowListener listener;
	
	private Account fetchedAccount;
	private MenuBar signalRecipient;
	private JFrame parent;
	private boolean alive;
	
	public LoginWindow(MenuBar signalRecipient, JFrame parent){
		super(new BorderLayout());
		
		alive = true;
		this.fetchedAccount = null;
		listener = new LoginWindowListener();
		this.signalRecipient = signalRecipient;
		this.parent = parent;
		this.addKeyListener(listener);
		
		this.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		this.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
		
		JPanel textFields = new JPanel(new GridLayout(2, 1));
		
		JPanel userNameArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
		userNameArea.add(new JLabel("Username:"));
		userName = new JTextField(20);
		userName.addKeyListener(listener);
		userNameArea.add(userName);
		textFields.add(userNameArea);
		
		JPanel passwordArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordArea.add(new JLabel("Password:"));
		password = new JPasswordField(20);
		password.addKeyListener(listener);
		passwordArea.add(password);
		textFields.add(passwordArea);
		
		this.add(textFields, BorderLayout.CENTER);
		
		JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirm = new JButton("Log in");
		cancel = new JButton("Cancel");
		confirm.setActionCommand("confirm");
		cancel.setActionCommand("cancel");
		confirm.addActionListener(listener);
		cancel.addActionListener(listener);
		buttonArea.add(confirm);
		buttonArea.add(cancel);
		this.add(buttonArea, BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(350, 200));
		this.setSize(new Dimension(350, 200));
		
	}
	
	public Account getFetchedAccount(){
		return fetchedAccount;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public JFrame getContainingFrame(){
		return parent;
	}
	
	public static LoginWindow showLoginWindow(MenuBar mb){
		JFrame frame = new JFrame("Login");
		frame.setSize(new Dimension(450, 200));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		LoginWindow window = new LoginWindow(mb, frame);
		frame.add(window);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		return window;
	}
	
	public class LoginWindowListener implements ActionListener, KeyListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
				case "confirm"	  : Account loginAttempt = DBInterface.checkPassword(userName.getText(), new String(password.getPassword()));
									if(loginAttempt == null){
										JOptionPane.showMessageDialog(null, "Username or Password is invalid.", "Login error", JOptionPane.ERROR_MESSAGE);
									}
									else{
										fetchedAccount = loginAttempt;
										signalRecipient.sendAccount(fetchedAccount);
										alive = false;
										if(parent != null){
											parent.dispose();
										}
									}
									break;
									
				case "cancel"     : signalRecipient.sendAccount(null);
									alive = false;
									if(parent != null){
										parent.dispose();
									}
									break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				actionPerformed(new ActionEvent(this, 0, "confirm"));
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
	}
	
	
	
}
