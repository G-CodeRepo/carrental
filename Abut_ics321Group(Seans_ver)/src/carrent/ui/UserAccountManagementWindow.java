package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Account;

public class UserAccountManagementWindow extends JPanel implements DatabaseConstants{
	public boolean alive;
	public JButton exit;
	private JFrame parent;
	private AccountManagementListener listener;
	private ChangePasswordWindow pwWindow;
	private Account activeAccount;
	public JComponent panel3;
	public JLabel notice;
	private JPanel textFields;
	private JTextField firstName, lastName, email, phoneNumber;
	private JButton changePassword, saveChanges;
	
	public UserAccountManagementWindow(Account acct, JFrame parent) {
		super(new BorderLayout());
        alive = true;
        this.parent = parent;
        activeAccount = acct;
        listener = new AccountManagementListener();
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        exit = new JButton("Exit");
        exit.setActionCommand("exit");
		exit.addActionListener(listener);
        
        buttonPanel.add(exit);
        
        JComponent panel1 = new AccountInfoPanel(acct);
        tabbedPane.addTab("<html><b>Account Information</b></html>", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        QueryBuilder builder = new QueryBuilder("*", TRANSACTION_TABLE);
        builder.addString(TRANSACTION_ACCOUNTNAME, activeAccount.getAccountName());
        JComponent panel2 = new TransactionScrollPane(DBInterface.queryTransactions(builder.toString()));
        tabbedPane.addTab("<html><b>Transaction History</b></html>", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        builder = new QueryBuilder("*", PAYMENT_OPTION_TABLE);
        builder.addString(PAYMENT_OPTION_ACCOUNT_NAME, activeAccount.getAccountName());
        panel3 = new PaymentOptionScrollPane(DBInterface.queryPaymentOptions(builder.toString()), activeAccount, this);
        tabbedPane.addTab("<html><b>Payment Options</b></html>", panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        //Add the tabbed pane to this panel.
        this.add(tabbedPane,BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
    
    public class AccountInfoPanel extends JPanel {

    	AccountInfoPanel(Account acct) {
    		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    		textFields = new JPanel(new GridLayout(6,2));
    		textFields.setMinimumSize(new Dimension(600, 200));
    		textFields.setPreferredSize(new Dimension(600, 200));
    		textFields.setMaximumSize(new Dimension(600, 200));
    		textFields.setAlignmentX(LEFT_ALIGNMENT);
    		
    		firstName = new JTextField(acct.getFirstName());
    		lastName = new JTextField(acct.getLastName());
    		phoneNumber = new JTextField( acct.getPhoneNumber());
    		email = new JTextField(acct.getEmail());
    		notice = new JLabel("");
    		
    		changePassword = new JButton("Change Password");
    		changePassword.setActionCommand("changePassword");
    		changePassword.addActionListener(listener);
    		
    		saveChanges = new JButton("Save Changes");
    		saveChanges.setActionCommand("saveChanges");
    		saveChanges.addActionListener(listener);
    		
    		textFields.add(new JLabel("<html><b>Account Name: "));
    		textFields.add(new JLabel("<html><b>" + activeAccount.getAccountName()));
    		textFields.add(new JLabel("<html><b>First Name: "));
    		textFields.add(firstName);
    		textFields.add(new JLabel("<html><b>Last Name: "));
    		textFields.add(lastName);
    		textFields.add(new JLabel("<html><b>Phone Number: "));
    		textFields.add(phoneNumber);
    		textFields.add(new JLabel("<html><b>Email Address: "));
    		textFields.add(email);
    		textFields.add(new JLabel("<html><b>Account Type: "));
    		if(acct.isEmployee()) {
    			textFields.add(new JLabel("<html><b>Employee/Administrator"));
    		} else {
    			textFields.add(new JLabel("<html><b>User"));
    		}
    		
    		notice.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 15));
    		notice.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
    		
    		textFields.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    		//changePassword.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
    		//saveChanges.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
    		
    		this.add(textFields);
    		this.add(changePassword);
    		this.add(saveChanges);
    		this.add(notice);

    	}
    }

    public static UserAccountManagementWindow showUserManagementWindow(Account activeAccount){
		JFrame frame = new JFrame("User Account");
		frame.setSize(new Dimension(900, 600));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UserAccountManagementWindow window = new UserAccountManagementWindow(activeAccount, frame);
		frame.add(window);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		return window;
	}
    
    
    public boolean isAlive(){
		return alive;
	}
    
    public class AccountManagementListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
				case "changePassword" :  if(pwWindow == null || !pwWindow.isAlive()){
										 	pwWindow  = ChangePasswordWindow.showChangePasswordWindow(activeAccount, notice);
				                         }
										 break;
				
				case "saveChanges"    :  activeAccount.setFirstName(firstName.getText());
										 activeAccount.setLastName(lastName.getText());
										 activeAccount.setEmail(email.getText());
										 activeAccount.setPhoneNumber(phoneNumber.getText());
										 DBInterface.updateAccount(activeAccount);
										 notice.setText("Changes Saved");
										 break;
										 
				case "exit"           :  alive = false;
									     if(parent != null ){
										     parent.dispose();
									     }
									     break;
  			
			}
		}
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    /*private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(750, 600));
		frame.setPreferredSize(new Dimension(750, 600));
		frame.setMaximumSize(new Dimension(750, 600));
        //Add content to the window.
        frame.add(new UserAccountManagementWindow(new Account("testAccount", "password", "firstName", "lastName", false)), BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            }
        });
    }*/
    
}

