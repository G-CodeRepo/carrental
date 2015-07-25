package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import carrent.entity.Transaction;

public class TransactionPanel extends JPanel implements MouseListener{
	
public static final String TRANSACTION_PANEL_ACTION_COMMAND = "TransactionPanelSelected";
	
	private static boolean compactMode = false;
	private static Dimension uncompressedSize = new Dimension(900, 400);
	private static Dimension compressedSize = new Dimension(900, 50);
	
	private Transaction transaction;
	private boolean compressed;
	
	private JPanel textPanel, transactionDateRow, titleRow, accountRow, tIDRow, vinRow, pickupRow, dropoffRow, startRow, endRow, paymentInfoRow, upfrontRow, penaltiesRow;
	private JLabel startDate, endDate, pickupDate, dropoffDate, vin, acctName, tID, upfront, penalties, paytype, transactionDate, tID2, transactionDate2, acctName2;
	
	public TransactionPanel(Transaction transaction){
		
		super(new BorderLayout());
		
		this.transaction = transaction;
		this.compressed = compactMode;
		
		textPanel = new JPanel(new GridLayout(11, 1));
		
		initializeLabels();
		
		titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		transactionDateRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tIDRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		accountRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		vinRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pickupRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dropoffRow =  new JPanel(new FlowLayout(FlowLayout.LEFT));
		startRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		endRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paymentInfoRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		upfrontRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		penaltiesRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		titleRow.add(tID2);
		titleRow.add(transactionDate2);
		titleRow.add(acctName2);
		
		transactionDateRow.add(new JLabel("<html><font size = 5><b>Transaction Date: </b></html>"));
		transactionDateRow.add(transactionDate);
		
		tIDRow.add(new JLabel("<html><font size = 5><b>Transaction ID: </b></html>"));
		tIDRow.add(tID);
		
		accountRow.add(new JLabel("<html><font size = 5><b>Account Name: "));
		accountRow.add(acctName);
		
		vinRow.add(new JLabel("<html><font size = 5><b>VIN: </b></html>"));
		vinRow.add(vin);
		
		startRow.add(new JLabel("<html><font size = 5><b>Rental Start Date: </b></html>"));
		startRow.add(startDate);
		
		endRow.add(new JLabel("<html><font size = 5><b>Rental End Date: </b></html>"));
		endRow.add(endDate);
		
		pickupRow.add(new JLabel("<html><font size = 5><b>Pickup Date: </b></html>"));
		pickupRow.add(pickupDate);
		
		dropoffRow.add(new JLabel("<html><font size = 5><b>Dropoff Date: </b></html>"));
		dropoffRow.add(dropoffDate);
		
		paymentInfoRow.add(new JLabel("<html><font size = 5><b>Payment ID: </b></html>"));
		paymentInfoRow.add(paytype);
		
		upfrontRow.add(new JLabel("<html><font size = 5><b>Upfront Payment: </b></html>"));
		upfrontRow.add(upfront);
		
		penaltiesRow.add(new JLabel("<html><font size = 5><b>Penalties: </b></html>"));
		penaltiesRow.add(penalties);
		
		this.addMouseListener(this);

		if(compressed){
			this.add(titleRow);
			this.setMaximumSize(compressedSize);
			this.setPreferredSize(compressedSize);
			this.setSize(compressedSize);
		}
		else{
			textPanel.add(transactionDateRow);
			textPanel.add(tIDRow);
			textPanel.add(accountRow);
			textPanel.add(vinRow);
			textPanel.add(pickupRow);
			textPanel.add(dropoffRow);
			textPanel.add(startRow);
			textPanel.add(endRow);
			textPanel.add(paymentInfoRow);
			textPanel.add(upfrontRow);
			textPanel.add(penaltiesRow);
			this.add(textPanel, BorderLayout.CENTER);
			this.setMaximumSize(uncompressedSize);
			this.setPreferredSize(uncompressedSize);
			this.setSize(uncompressedSize);
		}
		
	}
	public void setCompressed(boolean compressed){
		this.compressed = compressed;
		textPanel.removeAll();
		this.removeAll();
		
		if(compressed){
			this.add(titleRow);
			this.setMaximumSize(compressedSize);
			this.setPreferredSize(compressedSize);
			this.setSize(compressedSize);
		}
		else{
			
			textPanel.add(transactionDateRow);
			textPanel.add(tIDRow);
			textPanel.add(accountRow);
			textPanel.add(vinRow);
			textPanel.add(pickupRow);
			textPanel.add(dropoffRow);
			textPanel.add(startRow);
			textPanel.add(endRow);
			textPanel.add(paymentInfoRow);
			textPanel.add(upfrontRow);
			textPanel.add(penaltiesRow);
			this.add(textPanel, BorderLayout.CENTER);
			this.setMaximumSize(uncompressedSize);
			this.setPreferredSize(uncompressedSize);
			this.setSize(uncompressedSize);
		}
		
		this.revalidate();
	}
	
	private void initializeLabels(){
		
		startDate = new JLabel(transaction.getStartDate().toString() + " ");
		startDate.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		startDate.setBorder(BorderFactory.createEtchedBorder());
		endDate = new JLabel(transaction.getEndDate().toString() + " ");
		endDate.setBorder(BorderFactory.createEtchedBorder());
		endDate.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		pickupDate = new JLabel(transaction.getPickupDate().toString() + " ");
		pickupDate.setBorder(BorderFactory.createEtchedBorder());
		pickupDate.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		dropoffDate = new JLabel(transaction.getEndDate().toString() + " ");
		dropoffDate.setBorder(BorderFactory.createEtchedBorder());
		dropoffDate.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		vin = new JLabel(transaction.getVin() + " ");
		vin.setBorder(BorderFactory.createEtchedBorder());
		vin.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		acctName = new JLabel(transaction.getAcctName()+ " ");
		acctName.setBorder(BorderFactory.createEtchedBorder());
		acctName.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		acctName2 = new JLabel(transaction.getAcctName()+ " ");
		acctName2.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		acctName2.setBorder(BorderFactory.createEtchedBorder());
		tID = new JLabel(transaction.gettID() + " ");
		tID.setBorder(BorderFactory.createEtchedBorder());
		tID.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		tID2 = new JLabel(transaction.gettID() + " ");
		tID2.setBorder(BorderFactory.createEtchedBorder());
		tID2.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		upfront = new JLabel(formatMoney(transaction.getUpfront()) + " ");
		upfront.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		upfront.setBorder(BorderFactory.createEtchedBorder());
		penalties = new JLabel(formatMoney(transaction.getPenalties()) + " ");
		penalties.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		penalties.setBorder(BorderFactory.createEtchedBorder());
		paytype = new JLabel(transaction.getPayID() + " ");
		paytype.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		paytype.setBorder(BorderFactory.createEtchedBorder());
		transactionDate = new JLabel(transaction.getTransactionDate().toString() + " ");
		transactionDate.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		transactionDate.setBorder(BorderFactory.createEtchedBorder());
		transactionDate2 = new JLabel(transaction.getTransactionDate().toString() + " ");
		transactionDate2.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
		transactionDate2.setBorder(BorderFactory.createEtchedBorder());
	}
	
	private String formatMoney(int number) {
		return "$" + number + ".00";
	}
	
	public Transaction getBackingTransaction(){
		return this.transaction;
	}

	public void addActionListener(ActionListener l){
		listenerList.add(ActionListener.class, l);
	}
	
	public void removeActionListener(ActionListener l){
		listenerList.remove(ActionListener.class, l);
	}
	
	public static void setCompactMode(boolean compact){
		compactMode = compact;
	}
	
	public static boolean isCompactMode(){
		return compactMode;
	}
	
	public boolean isCompressed(){
		return this.compressed;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	//	if(e.getButton() == MouseEvent.BUTTON3){
	//		setCompressed(!compressed);
	//	}
	//	else{
			ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TRANSACTION_PANEL_ACTION_COMMAND);
			Object[] listeners = listenerList.getListenerList();
			for(int i = 0; i < listeners.length; i++){
				if(listeners[i] instanceof ActionListener){
					((ActionListener) listeners[i]).actionPerformed(event);
				}
			}
	//	}
	}
	
	// Other MouseListener methods are not used
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	public static void main(String[] args) throws IOException{
		JFrame testFrame = new JFrame();
		testFrame.setSize(uncompressedSize);
		
		Transaction t = new Transaction(new Timestamp(0),new Timestamp(0),new Timestamp(0),new Timestamp(0), "randomvin", "acctname", 1111111111, 2000, 30, 1, new Timestamp(0));
		
		testFrame.add(new TransactionPanel(t));
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setVisible(true);
	}
}
