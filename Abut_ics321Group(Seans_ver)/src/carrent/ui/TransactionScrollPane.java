package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import carrent.entity.Car;
import carrent.entity.Transaction;

public class TransactionScrollPane extends JPanel{
		
		private ArrayList<TransactionPanel> panels;
		private ArrayList<Transaction> transactionList;
		private JScrollPane scrollPane;
		private JPanel contentPanel;
		private TransactionPanel selectedPanel;
		private TransactionPanelListener listener;
		
		private Border selected, unselected;
		
		public TransactionScrollPane(ArrayList<Transaction> xactions){
			super(new BorderLayout()); 
			TransactionPanel.setCompactMode(true);
			listener = new TransactionPanelListener(); 
			
			this.transactionList = xactions;
			
			panels = new ArrayList<>();
			
			for(Transaction t : transactionList){
				panels.add(new TransactionPanel(t)); 
			}
			
			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
			

			selected = BorderFactory.createLoweredBevelBorder(); 
			unselected = BorderFactory.createRaisedBevelBorder();
			
			for(TransactionPanel p : panels){ 
				p.addActionListener(listener);
				p.setBorder(unselected);
				contentPanel.add(p);
			}
			
			if(contentPanel.getComponentCount() == 0){
				JPanel panel = new JPanel(false);
				JLabel filler = new JLabel("No Transactions Found");
				filler.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 15));
		        filler.setHorizontalAlignment(JLabel.CENTER);
		        panel.setLayout(new GridLayout(1, 1));
		        panel.add(filler);
				contentPanel.add(panel, SwingConstants.CENTER);
			}
			
			scrollPane = new JScrollPane(contentPanel); 
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(scrollPane, BorderLayout.CENTER);
			selectedPanel = null; 

			
		}
		
		
		
		public Transaction getSelectedTransaction(){
			if(selectedPanel == null){
				return null;
			}
			return selectedPanel.getBackingTransaction();
		}
		
		public class TransactionPanelListener implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 if(e.getActionCommand().equals(TransactionPanel.TRANSACTION_PANEL_ACTION_COMMAND)){ 
					for(TransactionPanel p : panels){											 
						if(!p.isCompressed() && TransactionPanel.isCompactMode()){
							p.setCompressed(true);
						}
						p.setBorder(unselected);
						if(e.getSource() == p){
							if(p.isCompressed()){
								p.setCompressed(false);
							}
							selectedPanel = p;
							p.setBorder(selected);
						}
					}
					contentPanel.revalidate();
					contentPanel.repaint();
				}
			}
		}

		
		public static void main(String[] args) throws IOException{
			JFrame frame = new JFrame();
			frame.setSize(new Dimension(460, 800));
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ArrayList<Transaction> xList = new ArrayList<Transaction>();
			for(int i = 0; i < 10; i++) {
				xList.add(new Transaction(new Timestamp(0),new Timestamp(0),new Timestamp(0),new Timestamp(0), Car.randomVIN(), "acctname", 1111111111, 2000, 30, 1, new Timestamp(0)));
			}
			
			frame.add(new TransactionScrollPane(xList));
			frame.setVisible(true);
		}
}
