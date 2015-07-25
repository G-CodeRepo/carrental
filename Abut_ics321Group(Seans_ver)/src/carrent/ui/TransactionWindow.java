package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;

import javax.swing.JPanel;


import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.ui.AdvancedSearchWindow.AdvancedSearchListener;
import carrent.util.ButtonFactory;


public class TransactionWindow extends JPanel implements DatabaseConstants{
	private JFrame parent, frame;
	private boolean alive;
	private JPanel buttonPanel, qsp;
	private MenuBar signalRecipient;
	
	public TransactionWindow(MenuBar signalRecipient, JFrame parent){
		
		super(new BorderLayout());
		this.frame = parent;
		this.alive = true;
		
		TransactionWindowListener listener = new TransactionWindowListener(this);
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(ButtonFactory.createButton("Cancel", "cancel", listener));
		this.add(new TransactionScrollPane(DBInterface.queryTransactions(new QueryBuilder("*", TRANSACTION_TABLE).toString())), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		
		
	}
	
	public JFrame getContainingFrame(){
		return parent;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public static TransactionWindow createWindow(MenuBar mb){
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(900, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		TransactionWindow w = new TransactionWindow(mb, frame);
		frame.add(w);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		return w;
		
	}
	
	public class TransactionWindowListener implements ActionListener{

		private TransactionWindow parent;
		
			public TransactionWindowListener(TransactionWindow parent){
				this.parent = parent;
			}
		
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()){
						
				
					case "cancel" : alive = false;
									if(frame != null){
									frame.dispose();
								
									}
			}
		
		}
	}
}