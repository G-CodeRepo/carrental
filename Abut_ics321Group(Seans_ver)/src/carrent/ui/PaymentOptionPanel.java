package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import carrent.entity.PaymentOption;

public class PaymentOptionPanel extends JPanel {
	
	private static Dimension uncompressedSize = new Dimension(900, 100);
	
	private PaymentOption paymentOption;
	private boolean compressed;
	private JRadioButton selectedButton;
	private JPanel textPanel;
	
	public PaymentOptionPanel(PaymentOption paymentOption){
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.paymentOption = paymentOption;
		
		StringBuilder cardNumObscured = new StringBuilder(paymentOption.getCardNumber());
		for(int i = 0; i < cardNumObscured.length() - 4; i++) {
			cardNumObscured.setCharAt(i, '*');
		}
		textPanel = new JPanel(new GridLayout(5, 2));
		
		textPanel.add(new JLabel("<html><b>Payment Type: "));
		textPanel.add(new JLabel("<html><b>" + paymentOption.getPaytype()));
		textPanel.add(new JLabel("<html><b>Card Holder: "));
		textPanel.add(new JLabel("<html><b>" + paymentOption.getCardHolder()));
		textPanel.add(new JLabel("<html><b>Card Number: "));
		textPanel.add(new JLabel("<html><b>" + cardNumObscured.toString()));
		textPanel.add(new JLabel("<html><b>Card Company: "));
		textPanel.add(new JLabel("<html><b>" + paymentOption.getCardCompany()));
		textPanel.add(new JLabel("<html><b>Expiration Date: "));
		textPanel.add(new JLabel("<html><b>" + paymentOption.getExpirationMonth() + "/" + paymentOption.getExpirationYear()));
		
		textPanel.setOpaque(false);
		textPanel.setMaximumSize(new Dimension(300,100));
		textPanel.setPreferredSize(new Dimension(300,100));
		textPanel.setSize(new Dimension(300,100));
		
		selectedButton = new JRadioButton();
		if(paymentOption.isSelected()) selectedButton.setSelected(true);
		
		
		this.add(selectedButton);
		this.add(Box.createRigidArea(new Dimension(40,100)));
		this.add(textPanel);
		
		this.setBackground(Color.white);
		this.setMaximumSize(uncompressedSize);
		this.setPreferredSize(uncompressedSize);
		this.setSize(uncompressedSize);
	}
	
	
	public PaymentOption getBackingOption(){
		return this.paymentOption;
	}

	public void addActionListener(ActionListener l){
		listenerList.add(ActionListener.class, l);
	}
	
	public void removeActionListener(ActionListener l){
		listenerList.remove(ActionListener.class, l);
	}
	
	public JRadioButton getButton() {
		return selectedButton;
	}

}
