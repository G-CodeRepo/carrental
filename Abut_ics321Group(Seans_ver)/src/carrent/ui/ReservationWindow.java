package carrent.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Account;
import carrent.entity.Car;
import carrent.entity.PaymentOption;
import carrent.entity.Transaction;
import carrent.util.ButtonFactory;
import carrent.util.Month;

public class ReservationWindow extends JPanel implements DatabaseConstants{

	private JComboBox<Month> month;
	private JSpinner day, year;
	private SpinnerNumberModel yearModel, dayModel;
	private Car c;
	private Account a;
	private CarScrollPane parent;
	private JFrame frame;
	private JLabel price;
	
	public ReservationWindow(Car c, Account a, CarScrollPane parent, JFrame frame){
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.c = c;
		this.a = a;
		this.parent = parent;
		this.frame = frame;
		
		this.add(Box.createVerticalStrut(20));
		ReservationWindowListener listener = new ReservationWindowListener();
		month = new JComboBox<>(Month.values());
		month.setActionCommand("month");
		month.addActionListener(listener);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearModel = new SpinnerNumberModel(currentYear, currentYear, currentYear + 2, 1);
		dayModel = new SpinnerNumberModel(1, 1, Month.JANUARY.getDayLimit(), 1);
		
		year = new JSpinner(yearModel);
		day = new JSpinner(dayModel);
		
		year.addChangeListener(listener);
		day.addChangeListener(listener);
		
		JPanel headline = new JPanel();
		headline.add(new JLabel("Renting " + c.getHeadline() + " to " + a.getAccountName(), SwingConstants.LEFT));
		this.add(headline);
		
		JPanel startTime = new JPanel(new FlowLayout());
		startTime.add(new JLabel(Calendar.getInstance().getTime().toString()));
		startTime.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Start Time", TitledBorder.LEFT, TitledBorder.TOP));
		this.add(startTime);
		
		JPanel endTime = new JPanel(new FlowLayout());
		
		endTime.add(month);
		endTime.add(day);
		endTime.add(new JLabel(", "));
		endTime.add(year);
		
		endTime.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "End Time", TitledBorder.LEFT, TitledBorder.TOP));
		this.add(endTime);
		
		JPanel currentPricePanel = new JPanel();
		currentPricePanel.add(new JLabel("Daily price for " + c.getHeadline() + ": $" + c.getRentalPrice()));
		this.add(currentPricePanel);
		
		JPanel pricePanel = new JPanel();
		pricePanel.add(new JLabel("Total rental price: $"));
		price = new JLabel("0");
		pricePanel.add(price);
		this.add(pricePanel);
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttons.add(ButtonFactory.createButton("Confirm", "confirm", listener));
		buttons.add(ButtonFactory.createButton("Cancel", "cancel", listener));
		this.add(buttons);
		this.add(Box.createVerticalGlue());
		
	}
	
	public long getEndTime(){
		return new GregorianCalendar((int) year.getValue(), ((Month) month.getSelectedItem()).getIndex(), (int) day.getValue()).getTimeInMillis();
	}
	
	public boolean checkTime(){
		return getEndTime() > System.currentTimeMillis();
	}
	
	public void updatePrice(){
		price.setText(getPrice() + "");
	}
	
	public int getPrice(){
		if(checkTime()){
			int days = (int) Math.ceil(((double) (getEndTime() - System.currentTimeMillis())) / 86400000.0);
			return days * c.getRentalPrice();
		}
		else{
			return 0;
		}
	}
	
	public class ReservationWindowListener implements ActionListener, ChangeListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
				case "month" : if(((int) day.getValue()) > ((Month) month.getSelectedItem()).getDayLimit()){
									day.setValue(((Month) month.getSelectedItem()).getDayLimit());
								}
								dayModel.setMaximum(((Month) month.getSelectedItem()).getDayLimit());
								break;
							   
				case "confirm" : if(checkTime()){ 
									QueryBuilder builder = new QueryBuilder(" * ", PAYMENT_OPTION_TABLE);
									builder.addString(PAYMENT_OPTION_ACCOUNT_NAME, a.getAccountName());
									builder.addBool(PAYMENT_OPTION_SELECTED, true);
									ArrayList<PaymentOption> options = DBInterface.queryPaymentOptions(builder.toString());
									if(options.size() == 0) {
										JOptionPane.showMessageDialog(null, "No Payment Method Selected. Please Select a Default Payment Method in the Account Settings.", "Rental error", JOptionPane.ERROR_MESSAGE);
										break;
									}
									else {
									Transaction t = new Transaction(new Timestamp(System.currentTimeMillis()), new Timestamp(getEndTime()), 
																	new Timestamp(System.currentTimeMillis()), new Timestamp(getEndTime()), 
																	c.getVIN(), a.getAccountName(), DBInterface.getNextTID(), getPrice(), 0, options.get(0).getPayID(), 
																	new Timestamp(System.currentTimeMillis()));
									DBInterface.setRentalStatus(c.getVIN(), true);
									parent.sendTransaction(t);
									}
								 }
							  	 else{
									JOptionPane.showMessageDialog(null, "Invalid end date selected.", "Rental error", JOptionPane.ERROR_MESSAGE);
									break;
								 }
				case "cancel" : parent.sendWindowClosedEvent();
								frame.dispose();
								break;
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			updatePrice();			
		}
		
		
		
	}
	
	public static void showReservationWindow(Car c, Account a, CarScrollPane parent){
		JFrame frame = new JFrame("Reserving " + c.getHeadline());
		ReservationWindow window = new ReservationWindow(c, a, parent, frame);
		
		frame.setSize(new Dimension(400, 375));
		frame.add(window);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
