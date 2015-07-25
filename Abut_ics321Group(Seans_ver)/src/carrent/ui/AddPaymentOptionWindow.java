package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import carrent.db.DBInterface;
import carrent.entity.Account;
import carrent.entity.PaymentOption;

public class AddPaymentOptionWindow extends JPanel{

	private AddPaymentOptionListener listener;
	private PaymentOption createdOption;
	
	private JTextField payType, cardHolder, cardNumber, csv;
	private ButtonGroup companies;
	private JComboBox expirationMonth, expirationYear;
	private JButton confirm, cancel;
	private JFrame parent;
	private JRadioButton visa, mastercard, discover, americanExpress;
	private Account activeAccount;
	public JLabel notice;
	private boolean alive;
	
	public AddPaymentOptionWindow(JFrame parent, JLabel notice, Account active){
		
		super(new BorderLayout());

		listener = new AddPaymentOptionListener();
		createdOption = null;
		this.parent = parent;
		alive = true;
		this.notice = notice;
		this.activeAccount = active;
		
		this.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		this.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
		
		JPanel textFields = new JPanel();
		textFields.setLayout(new BoxLayout(textFields,BoxLayout.Y_AXIS));
		
		JPanel cardCompanyGroup = new JPanel();
		cardCompanyGroup.setLayout(new BoxLayout(cardCompanyGroup, BoxLayout.Y_AXIS));
		JPanel cardTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		companies = new ButtonGroup();
		cardTextPanel.add(new JLabel("Card Company"));
		cardTextPanel.setMaximumSize(new Dimension(440, 20));
		cardCompanyGroup.add(cardTextPanel);
		
		visa = new JRadioButton("VISA");
		visa.setActionCommand("VISA");
		mastercard = new JRadioButton("MasterCard");
		mastercard.setActionCommand("VISA");
		discover = new JRadioButton("Discover");
		discover.setActionCommand("VISA");
		americanExpress = new JRadioButton("American Express");
		americanExpress.setActionCommand("American Express");
		
		companies.add(visa);
		companies.add(mastercard);
		companies.add(discover);
		companies.add(americanExpress);
		
		JPanel cardCompanyButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cardCompanyButtonGroup.add(visa);
		cardCompanyButtonGroup.add(mastercard);
		cardCompanyButtonGroup.add(discover);
		cardCompanyButtonGroup.add(americanExpress);
		cardCompanyButtonGroup.setMaximumSize(new Dimension(440, 40));
		cardCompanyGroup.add(cardCompanyButtonGroup);
		
		textFields.add(cardCompanyGroup);
		
		JPanel cardHolderGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cardHolderGroup.add(new JLabel("Card Holder Name:"));
		cardHolder = new JTextField(20);
		cardHolderGroup.add(cardHolder);
		cardHolderGroup.setMaximumSize(new Dimension(440, 50));
		textFields.add(cardHolderGroup);
		
		JPanel cardNumberGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cardNumberGroup.add(new JLabel("Card Number:     "));
		                                
		cardNumber = new JTextField(20);
		cardNumberGroup.add(cardNumber);
		cardNumberGroup.setMaximumSize(new Dimension(440, 50));
		textFields.add(cardNumberGroup);
		
		JPanel csvGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		csvGroup.add(new JLabel("CSV:             "));
		csv = new JTextField(5);
		csvGroup.setMaximumSize(new Dimension(440, 50));
		csvGroup.add(csv);
		
		textFields.add(csvGroup);
		
		ArrayList<Integer> months = new ArrayList();
		for(int i = 1; i < 13; i++) {
			months.add(i);
		}
		ArrayList<Integer> years = new ArrayList();
		Calendar now = Calendar.getInstance(); 
		int currentYear = now.get(Calendar.YEAR);
		for(int i = currentYear; i < currentYear + 10; i++) {
			years.add(i);
		}
		
		JPanel expirationDateGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		expirationDateGroup.add(new JLabel("Expiration Date:"));
		expirationMonth = new JComboBox(months.toArray());
		expirationYear = new JComboBox(years.toArray());
		expirationDateGroup.add(expirationMonth);
		expirationDateGroup.add(expirationYear);
		expirationDateGroup.setMaximumSize(new Dimension(440, 50));
		textFields.add(expirationDateGroup);
		
		this.add(textFields, BorderLayout.CENTER);
		
		JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		confirm = new JButton("Add");
		confirm.setActionCommand("add");
		confirm.addActionListener(listener);
		buttonGroup.add(confirm);
		
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(listener);
		buttonGroup.add(cancel);
		
		this.add(buttonGroup, BorderLayout.SOUTH);
		
		
	}
	
	public PaymentOption getCreatedOption(){
		return createdOption;
	}
	
	public JFrame getContainingFrame(){
		return parent;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public static AddPaymentOptionWindow showAddPaymentOptionWindow(JLabel notice, Account active){
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(500, 400));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		AddPaymentOptionWindow w = new AddPaymentOptionWindow(frame, notice, active);
		frame.add(w);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		return w;
		
	}
	
	public class AddPaymentOptionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
				case "add"       : 	
									if(cardHolder.getText().length() == 0){
										JOptionPane.showMessageDialog(null, "Card Holder Name cannot be empty.", "Add Payment Option Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(cardNumber.getText().length() == 0){
										JOptionPane.showMessageDialog(null, "Card Number cannot be empty.", "Add Payment Option Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(csv.getText().length() == 0){
										JOptionPane.showMessageDialog(null, "CSV code cannot be empty.", "Add Payment Option Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if(companies.getSelection() == null){
										JOptionPane.showMessageDialog(null, "Select a Card Company.", "Add Payment Option Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
									PaymentOption p = new PaymentOption("Credit Card", DBInterface.getNextPID(), activeAccount.getAccountName(), cardHolder.getText(), cardNumber.getText(),
								             companies.getSelection().getActionCommand(), Integer.parseInt(csv.getText()), Integer.parseInt(expirationMonth.getSelectedItem().toString()), Integer.parseInt(expirationYear.getSelectedItem().toString()), false);
									long time = System.currentTimeMillis();
									int status = DBInterface.addPaymentOption(p);
									time = System.currentTimeMillis() - time;
									MainPanel.conditionalPrint(String.format("QUERY - Account written to DB in %.3f seconds.", ((double) time) / 1000));
									if(status != 0){
											System.err.println("ERROR - Cannot connect to account DB");
											JOptionPane.showMessageDialog(null, "Failed to connect to account server.", "Account creation error", JOptionPane.ERROR_MESSAGE);
											return;
									} else{
										createdOption = p;
										notice.setText("Successfully Added New Payment Option");
									}
									alive = false;
									if(parent != null ){
										parent.dispose();
									}
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
