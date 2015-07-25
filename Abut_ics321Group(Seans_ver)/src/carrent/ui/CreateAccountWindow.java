package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

public class CreateAccountWindow extends JPanel{

	private CreateAccountWindowListener listener;
	private Account createdAccount;
	
	private JTextField username, firstname, lastname;
	private JPasswordField password, passwordConfirm;
	private JButton confirm, cancel;
	private JFrame parent;
	private MenuBar signalRecipient;
	private boolean alive;
	
	public CreateAccountWindow(MenuBar signalRecipient, JFrame parent){
		
		super(new BorderLayout());

		listener = new CreateAccountWindowListener();
		createdAccount = null;
		this.parent = parent;
		this.signalRecipient = signalRecipient;
		alive = true;
		
		this.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		this.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
		
		JPanel textFields = new JPanel(new GridLayout(5, 1));
		
		JPanel usernameGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		usernameGroup.add(new JLabel("Username:"));
		username = new JTextField(20);
		usernameGroup.add(username);
		textFields.add(usernameGroup);
		
		JPanel passwordGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passwordGroup.add(new JLabel("Password:"));
		password = new JPasswordField(20);
		passwordGroup.add(password);
		textFields.add(passwordGroup);
		
		JPanel passwordConfirmGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passwordConfirmGroup.add(new JLabel("Confirm Password:"));
		passwordConfirm = new JPasswordField(20);
		passwordConfirmGroup.add(passwordConfirm);
		textFields.add(passwordConfirmGroup);
		
		JPanel firstnameGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		firstnameGroup.add(new JLabel("First Name:"));
		firstname = new JTextField(20);
		firstnameGroup.add(firstname);
		textFields.add(firstnameGroup);
		
		JPanel lastnameGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		lastnameGroup.add(new JLabel("Last Name:"));
		lastname = new JTextField(20);
		lastnameGroup.add(lastname);
		textFields.add(lastnameGroup);
		
		this.add(textFields, BorderLayout.CENTER);
		
		JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		confirm = new JButton("Create Account");
		confirm.setActionCommand("confirm");
		confirm.addActionListener(listener);
		buttonGroup.add(confirm);
		
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(listener);
		buttonGroup.add(cancel);
		
		this.add(buttonGroup, BorderLayout.SOUTH);
		
		
	}
	
	public Account getCreatedAccount(){
		return createdAccount;
	}
	
	public JFrame getContainingFrame(){
		return parent;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public static CreateAccountWindow createAccount(MenuBar mb){
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(500, 300));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		CreateAccountWindow w = new CreateAccountWindow(mb, frame);
		frame.add(w);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		return w;
		
	}
	
	public class CreateAccountWindowListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
				case "confirm" : 	if(!Arrays.equals(password.getPassword(), passwordConfirm.getPassword())){
										JOptionPane.showMessageDialog(null, "Given passwords do not match.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(username.getText().length() == 0){
										JOptionPane.showMessageDialog(null, "Username cannot be empty.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(password.getPassword().length == 0){
										JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(firstname.getText().length() == 0){
										JOptionPane.showMessageDialog(null, "First name cannot be empty.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(lastname.getText().length() == 0){
										JOptionPane.showMessageDialog(null, "Last name cannot be empty.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									Account a = new Account(username.getText(), new String(password.getPassword()), firstname.getText(), lastname.getText(), false);
									long time = System.currentTimeMillis();
									int status = DBInterface.createAccount(a);
									time = System.currentTimeMillis() - time;
									MainPanel.conditionalPrint(String.format("QUERY - Account written to DB in %.3f seconds.", ((double) time) / 1000));
									if(status != 0){
										if(status == 1){
											JOptionPane.showMessageDialog(null, "Account \"" + username.getText() + "\" already exists.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										}
										else{
											System.err.println("ERROR - Cannot connect to account DB");
											JOptionPane.showMessageDialog(null, "Failed to connect to account server.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										}
										return;
									}
									else{
										createdAccount = a;	
										
									}
									alive = false;
									signalRecipient.sendAccount(a);
									if(parent != null ){
										parent.dispose();
									}
									break;
									
				case "cancel" :		alive = false;
									signalRecipient.sendAccount(null);
									if(parent != null ){
										parent.dispose();
									}
									break;
  			
			}
		}

	}
	
}
