package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Account;
import carrent.entity.Car;
import carrent.entity.PaymentOption;
import carrent.entity.Transaction;
import carrent.ui.UserAccountManagementWindow.AccountManagementListener;

public class PaymentOptionScrollPane extends JPanel implements DatabaseConstants {
		
		private ArrayList<PaymentOptionPanel> panels;
		private ArrayList<PaymentOption> optionList;
		private JScrollPane scrollPane;
		private UserAccountManagementWindow parent;
		private JPanel contentPanel, buttonPanel;
		private JButton setDefault, addOption, refresh;
		private PaymentOptionScrollPaneListener listener;
		private Account activeAccount;
		private AddPaymentOptionWindow addWindow;
		public JLabel notice;
		ButtonGroup group;
		
		public PaymentOptionScrollPane(ArrayList<PaymentOption> options, Account active, UserAccountManagementWindow parent){
			super(new BorderLayout()); 
			this.parent = parent;
			this.optionList = options;
			activeAccount = active;
			panels = new ArrayList<>();
			listener = new PaymentOptionScrollPaneListener();
			
			for(PaymentOption p : optionList){
				panels.add(new PaymentOptionPanel(p));
			}
			
			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
			
			setDefault = new JButton("Select");
			setDefault.setActionCommand("setDefault");
    		setDefault.addActionListener(listener);
    		
    		addOption = new JButton("Add New Payment Option");
			addOption.setActionCommand("addOption");
    		addOption.addActionListener(listener);
    		
    		refresh = new JButton("Refresh");
			refresh.setActionCommand("refresh");
    		refresh.addActionListener(listener);
			
    		notice = new JLabel(" ");
    		notice.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
    		notice.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 15));
    		
			buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			buttonPanel.add(setDefault);
			buttonPanel.add(addOption);
			buttonPanel.add(refresh);
			//buttonPanel.add(Box.createRigidArea(new Dimension(350,10)));
			buttonPanel.add(notice);
			
			group = new ButtonGroup();
			for(PaymentOptionPanel p : panels){ 
				group.add(p.getButton());
				p.setBorder(BorderFactory.createRaisedBevelBorder());
				contentPanel.add(p);
			}
			
			if(contentPanel.getComponentCount() == 0){
				JPanel panel = new JPanel(false);
				JLabel filler = new JLabel("No Payment Options Found");
				filler.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 15));
		        filler.setHorizontalAlignment(JLabel.CENTER);
		        panel.setLayout(new GridLayout(1, 1));
		        panel.add(filler);
				contentPanel.add(panel, SwingConstants.CENTER);
			}
			
			scrollPane = new JScrollPane(contentPanel); 
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(scrollPane, BorderLayout.CENTER);
			this.add(buttonPanel, BorderLayout.SOUTH);

			
		}
		
		public void refresh() {
			QueryBuilder builder = new QueryBuilder("*", PAYMENT_OPTION_TABLE);
	        builder.addString(PAYMENT_OPTION_ACCOUNT_NAME, activeAccount.getAccountName());
			parent.panel3 = new PaymentOptionScrollPane(DBInterface.queryPaymentOptions(builder.toString()), activeAccount, parent);
			parent.repaint();
			parent.validate();
		}
		
		public class  PaymentOptionScrollPaneListener implements ActionListener{
				
				@Override
				public void actionPerformed(ActionEvent e) {
					switch(e.getActionCommand()){
					
						case "setDefault" : for(PaymentOptionPanel p: panels) {
												PaymentOption option = p.getBackingOption();
												option.setSelected(true);
												if(p.getButton().isSelected()) {
													DBInterface.updatePaymentOption(option.getPayID(), PAYMENT_OPTION_SELECTED, "1");
												} else {
													DBInterface.updatePaymentOption(option.getPayID(), PAYMENT_OPTION_SELECTED, "0");
												}
												notice.setText("Default Payment Option Set");
						                    }
						                    break;
						
						case "addOption" : if(addWindow == null || !addWindow.isAlive()){
						 	                  addWindow  = AddPaymentOptionWindow.showAddPaymentOptionWindow(notice, activeAccount);
                                            }
						                    break;
						                    
						case "refresh" :    refresh();
                          				   
		                                    break;

		  			
					}
				}
		    }
		
}
