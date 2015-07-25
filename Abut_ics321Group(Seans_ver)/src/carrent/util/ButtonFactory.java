package carrent.util;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonFactory {

	public static JButton createButton(String buttonText, String actionCommand, ActionListener listener){
		JButton button = new JButton(buttonText);
		button.setActionCommand(actionCommand);
		button.addActionListener(listener);
		
		return button;
	}
	
}
