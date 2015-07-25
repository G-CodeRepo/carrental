package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Account;
import carrent.util.ButtonFactory;

public class AccountListingWindow extends JPanel implements DatabaseConstants{

	private JList<Account> list;
	private JFrame frame;
	private DefaultListModel<Account> listModel;
	private Account activeAccount;
	private boolean alive;
	
	public AccountListingWindow(Account activeAccount, JFrame frame){
		
		super(new BorderLayout());
		AccountListingWindowListener listener = new AccountListingWindowListener(this);
		this.frame = frame;
		this.activeAccount = activeAccount;
		this.alive = true;
		
		JPanel buttons = new JPanel(new GridLayout(5, 1, 0, 5));
		
		buttons.add(ButtonFactory.createButton("Edit", "edit", listener));
//		buttons.add(ButtonFactory.createButton("Sort", "sort", listener));
		buttons.add(ButtonFactory.createButton("Delete", "delete", listener));
		buttons.add(ButtonFactory.createButton("Refresh", "refresh", listener));
		buttons.add(ButtonFactory.createButton("Close", "close", listener));
		
		ArrayList<Account> initialAccountList = DBInterface.queryAccounts(new QueryBuilder("*", ACCOUNT_TABLE).toString());
		if(activeAccount != null){
			initialAccountList.remove(activeAccount);
		}
		
		listModel = new DefaultListModel<>();
		for(Account a : initialAccountList){
			listModel.addElement(a);
		}
		
		list = new JList<>(listModel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(buttons);
		rightPanel.add(Box.createVerticalGlue());
		
		this.add(list, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
		
	}
	
	public void delete(){
		if(list.getSelectedValue() == null){
			JOptionPane.showMessageDialog(this, "No account selected.", "Delete error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int option = JOptionPane.showConfirmDialog(this, "Really delete account \"" + list.getSelectedValue().getAccountName() +
									  			   "\"?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			int code = DBInterface.deleteAccount(list.getSelectedValue().getAccountName());
			if(code != 0){
				System.err.println("ERROR - Unable to delete account.");
			}
			refresh();
		}
	}
	
	public void refresh(){
		listModel.removeAllElements();
		ArrayList<Account> accountList = DBInterface.queryAccounts(new QueryBuilder("*", ACCOUNT_TABLE).toString());
		if(activeAccount != null){
			accountList.remove(activeAccount);
		}
		for(Account a : accountList){
			listModel.addElement(a);
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public class AccountListingWindowListener implements ActionListener{

		private AccountListingWindow parent;
		
		public AccountListingWindowListener(AccountListingWindow parent){
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
			case "delete" : parent.delete();
			case "refresh" : parent.refresh();
							 break;
			case "close" : parent.frame.dispose();
						   alive = false;
						   break;
			}
			
		}
		
	}
	
	public static AccountListingWindow showAccountListingWindow( Account activeAccount){
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(400, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		AccountListingWindow window = new AccountListingWindow(activeAccount, frame);
		
		frame.add(window);
		
		frame.setVisible(true);
		return window;
	}
	
	public static void main(String[] args) throws ClassNotFoundException{
		showAccountListingWindow(null);
	}
	
}
