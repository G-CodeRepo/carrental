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
import carrent.db.DatabaseConstants;
import carrent.entity.Account;

public class ChangePasswordWindow extends JPanel implements DatabaseConstants{

	private CreateChangePasswordListener listener;
	private Account activeAccount;
	private JPasswordField password, passwordConfirm, passwordOld;
	private JButton confirm, cancel;
	private JFrame parent;
	private JLabel notice;
	private boolean alive;
	
	public ChangePasswordWindow(Account acct, JLabel notice, JFrame parent){
		
		super(new BorderLayout());
		listener = new CreateChangePasswordListener();
		this.parent = parent;
		activeAccount = acct;
		this.notice = notice;
		alive = true;
		
		this.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		this.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
		
		JPanel textFields = new JPanel(new GridLayout(3, 1));
		
		JPanel passwordOldGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passwordOldGroup.add(new JLabel("Old Password:"));
		passwordOld = new JPasswordField(20);
		passwordOldGroup.add(passwordOld);
		textFields.add(passwordOldGroup);
		
		JPanel passwordNewGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passwordNewGroup.add(new JLabel("New Password:"));
		password = new JPasswordField(20);
		passwordNewGroup.add(password);
		textFields.add(passwordNewGroup);
		
		JPanel passwordConfirmGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passwordConfirmGroup.add(new JLabel("Confirm Password:"));
		passwordConfirm = new JPasswordField(20);
		passwordConfirmGroup.add(passwordConfirm);
		textFields.add(passwordConfirmGroup);
		
		this.add(textFields, BorderLayout.CENTER);
		
		JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		confirm = new JButton("Change Password");
		confirm.setActionCommand("confirm");
		confirm.addActionListener(listener);
		buttonGroup.add(confirm);
		
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(listener);
		buttonGroup.add(cancel);
		
		this.add(buttonGroup, BorderLayout.SOUTH);
		
		
	}
	
	
	public JFrame getContainingFrame(){
		return parent;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public static ChangePasswordWindow showChangePasswordWindow(Account acct, JLabel notice){
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(500, 300));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ChangePasswordWindow w = new ChangePasswordWindow(acct, notice, frame);
		frame.add(w);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		return w;
		
	}
	
	public class CreateChangePasswordListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
				case "confirm" : 	if(!activeAccount.getPassword().equals(new String(passwordOld.getPassword()))) {
										JOptionPane.showMessageDialog(null, "Your entered your original password incorrectly.", "Password Change", JOptionPane.ERROR_MESSAGE);
										passwordOld.setText(null);
										return;
				                    }
									if(!Arrays.equals(password.getPassword(), passwordConfirm.getPassword())){
										JOptionPane.showMessageDialog(null, "Given passwords do not match.", "Password Change", JOptionPane.ERROR_MESSAGE);
										password.setText(null);
										passwordConfirm.setText(null);
										return;
									}
									if(password.getPassword().length == 0){
										JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Password Change", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(passwordConfirm.getPassword().length == 0){
										JOptionPane.showMessageDialog(null, "Please Confirm the Password.", "Password Change", JOptionPane.ERROR_MESSAGE);
										return;
									}
									long time = System.currentTimeMillis();
									activeAccount.setPassword(new String(password.getPassword()));
									int status = DBInterface.updateAccount(activeAccount.getAccountName(), ACCOUNT_PASSWORD, activeAccount.getPassword());
									time = System.currentTimeMillis() - time;
									MainPanel.conditionalPrint(String.format("QUERY - Account written to DB in %.3f seconds.", ((double) time) / 1000));
									if(status != 0){
										if(status == 1){
											System.err.println("ERROR - Cannot connect to account DB");
											JOptionPane.showMessageDialog(null, "Failed to connect to account server.", "Account creation error", JOptionPane.ERROR_MESSAGE);
										}
										return;
									}
					
									alive = false;
									if(parent != null ){
										parent.dispose();
									}
									notice.setText("Password Changed");
									break;
									
				case "cancel" :		alive = false;
									if(parent != null ){
										parent.dispose();
									}
									break;
  			
			}
		}
	}
}
