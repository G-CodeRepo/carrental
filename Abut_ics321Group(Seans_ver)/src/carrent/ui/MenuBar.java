package carrent.ui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import carrent.db.CarDBPopulator;
import carrent.db.DBInterface;
import carrent.entity.Account;

public class MenuBar extends JMenuBar {
	
	private JMenu userMenu, adminMenu, debugMenu;
	private JMenuItem login, logout, createAccount, viewAccounts, viewAccount, viewTransactions, resetCarDB, modifyCars;
	private MenuBarListener listener;
	private LoginWindow loginWindow;
	private CreateAccountWindow createAccountWindow;
	private TransactionWindow transactionWindow;
	private AccountListingWindow accountListingWindow;
	private UserAccountManagementWindow accountManagementWindow;
	private CarModificationWindow carModificationWindow;
	
	private Account activeAccount;
	
	
	public MenuBar(MainPanel parent){
		super();
		activeAccount = null;
		createAccountWindow = null;
		viewAccount = null;
		listener = new MenuBarListener(this);
		loginWindow = null;
		transactionWindow = null;
		accountListingWindow = null;
		
		this.add(Box.createHorizontalGlue());
		
		userMenu = new JMenu("User Accounts");
		userMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		userMenu.addMouseListener(new TopLevelMenuListener());
		
		login = new JMenuItem("Log in");
		logout = new JMenuItem("Not logged in");
		createAccount = new JMenuItem("Create account");
		viewAccount = new JMenuItem("View account");
		
		login.setActionCommand("login");
		logout.setActionCommand("logout");
		createAccount.setActionCommand("createAccount");
		viewAccount.setActionCommand("viewAccount");
		
		login.addActionListener(listener);
		logout.addActionListener(listener);
		createAccount.addActionListener(listener);
		viewAccount.addActionListener(listener);
		
		logout.setEnabled(false);
		viewAccount.setEnabled(false);
		
		userMenu.add(login);
		userMenu.add(logout);
		userMenu.add(createAccount);
		userMenu.add(viewAccount);
		
		adminMenu = new JMenu("Admin Tools");
		adminMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		viewAccounts = new JMenuItem("View Accounts");
		viewAccounts.setActionCommand("viewaccounts");
		viewAccounts.addActionListener(listener);
		
		viewTransactions = new JMenuItem("View Transactions");
		viewTransactions.setActionCommand("viewtransactions");
		viewTransactions.addActionListener(listener);
		
		modifyCars = new JMenuItem("Modify Car Database");
		modifyCars.setActionCommand("modifyCars");
		modifyCars.addActionListener(listener);
		
		adminMenu.add(viewAccounts);
		adminMenu.add(viewTransactions);
		adminMenu.add(modifyCars);
		
		debugMenu = new JMenu("Debug Menu");
		
		resetCarDB = new JMenuItem("Reset Car DB");
		resetCarDB.setActionCommand("resetdb");
		resetCarDB.addActionListener(listener);
		debugMenu.add(resetCarDB);
		
		if(MainPanel.debugMenu){
			this.add(debugMenu);
		}
		
		this.add(userMenu);
	}
	
	public Account getActiveAccount(){
		return activeAccount;
	}
	
	public void sendAccount(Account a){
		activeAccount = a;
		if(activeAccount != null){
			   login.setText("Logged in as " + activeAccount.getAccountName());
			   login.setEnabled(false);
			   logout.setText("Log out");
			   logout.setEnabled(true);
			   createAccount.setEnabled(false);
			   viewAccount.setEnabled(true);
			   if(a.isEmployee()){
				   this.remove(userMenu);
				   this.add(adminMenu);
				   this.add(userMenu);
				   this.revalidate();
				   this.repaint();
			   }
			   else{
				   this.remove(adminMenu);
				   this.revalidate();
				   this.repaint();
			   }
			   MainPanel.conditionalPrint("LOG - Logged in as " + a.getAccountName());
		}
		else{
			this.remove(adminMenu);
			this.revalidate();
			this.repaint();
		}
	}
	
	public class MenuBarListener implements ActionListener{
		
		private MenuBar parent;
		
		public MenuBarListener(MenuBar parent){
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand()){
			
				case "viewtransactions" : if(transactionWindow == null || !transactionWindow.isAlive()){
										 	  transactionWindow = TransactionWindow.createWindow(parent);
										  }	
										  break;
										  
				case "viewaccounts" : if(accountListingWindow == null || !accountListingWindow.isAlive()){
										  accountListingWindow = AccountListingWindow.showAccountListingWindow(activeAccount);
									  }
									  break;
				case "viewAccount" : if(accountManagementWindow == null || !accountManagementWindow.isAlive()){
										 accountManagementWindow = UserAccountManagementWindow.showUserManagementWindow(activeAccount);
									 }
									 break;
				case "modifyCars" : if(carModificationWindow == null || !carModificationWindow.isAlive()) {
										if(CarScrollPane.selectedPanel != null) carModificationWindow = CarModificationWindow.showCarModificationWindow(CarScrollPane.selectedPanel.getBackingCar());
										else CarModificationWindow.showCarModificationWindow(null);
				                     }
									 break;
									
				case "login" :	loginWindow = LoginWindow.showLoginWindow(parent);
							  	break;
							   
				case "logout" : activeAccount = null;
								login.setText("Log in");
								login.setEnabled(true);
								logout.setText("Not logged in");
								logout.setEnabled(false);
								viewAccount.setEnabled(false);
								createAccount.setEnabled(true);
								parent.remove(adminMenu);
								parent.revalidate();
								parent.repaint();
								MainPanel.conditionalPrint("LOG - Successfully logged out.");
								break;
								
				case "createAccount" :	createAccountWindow = CreateAccountWindow.createAccount(parent);
										break;
										
				case "resetdb" : int choice = JOptionPane.showConfirmDialog(null, "Really reset car database?\n(This operation takes upwards of 1 minute to complete.)", 
											  "Reset DB confirmation", JOptionPane.YES_NO_OPTION);
								 if(choice == JOptionPane.YES_OPTION){
									 System.err.println("DEBUG - Clearing car database");
									 long time = System.currentTimeMillis();
									 int code = DBInterface.removeAllCars();
									 if(code != 0){
										 System.err.println("ERROR - Unable to remove cars");
									 }
									 else{
										 time = System.currentTimeMillis() - time;
										 System.err.println(String.format("DEBUG - Removal completed in %.3f seconds.", ((double) time) / 1000));
										 System.err.println("DEBUG - Regenerating car entries");
										 time = System.currentTimeMillis();
										 try {
											CarDBPopulator.populateDB(false);
											time = System.currentTimeMillis() - time;
											System.err.println(String.format("DEBUG - Regeneration completed in %.3f seconds.", ((double) time) / 1000));
										} catch (NumberFormatException
												| ClassNotFoundException
												| SQLException | IOException ex) {
											System.err.println("ERROR - Problem encountered adding car entry: " + ex.getMessage());
										}
										 
									 }
								 }
			}
		}	
	}
	
	// Top level menu listener can be replaced with better event handling in LoginWindow and CreateAccountWindow
	public class TopLevelMenuListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if((loginWindow != null && loginWindow.isAlive()) || (createAccountWindow != null && createAccountWindow.isAlive())){
								login.setEnabled(false);
								createAccount.setEnabled(false);
							}
			else if(activeAccount == null){
				login.setEnabled(true);
				createAccount.setEnabled(true);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			mouseClicked(e);
		}
			
		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
}
