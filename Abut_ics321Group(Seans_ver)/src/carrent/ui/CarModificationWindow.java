package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Account;
import carrent.entity.Car;

public class CarModificationWindow extends JPanel implements DatabaseConstants{
	public boolean alive;
	public JButton exit;
	private JFrame parent;
	private CarModificationListener listener;
	private Car selectedCar;
	public JLabel notice;

	public CarModificationWindow(JFrame parent, Car selected) {
		super(new BorderLayout());
		selectedCar = selected;
        alive = true;
        this.parent = parent;
        listener = new CarModificationListener();
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        exit = new JButton("Exit");
        exit.setActionCommand("exit");
		exit.addActionListener(listener);
        
        buttonPanel.add(exit);
        
        JComponent panel1 = new ModifyCarPanel(selectedCar);;
        tabbedPane.addTab("<html><b>Modify Car</b></html>", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = new AddCarPanel();
        tabbedPane.addTab("<html><b>Add Car</b></html>", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        //Add the tabbed pane to this panel.
        this.add(tabbedPane,BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    

    public static CarModificationWindow showCarModificationWindow(Car c){
		JFrame frame = new JFrame("Modify Cars");
		frame.setSize(new Dimension(900, 600));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		CarModificationWindow window = new CarModificationWindow(frame, c);
		frame.add(window);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		return window;
	}
    
    
    public boolean isAlive(){
		return alive;
	}
    
    public class CarModificationListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){							 
				case "exit"           :  alive = false;
									     if(parent != null ){
										     parent.dispose();
									     }
									     break;
  			
			}
		}
    }
}