package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import carrent.entity.Car;
import carrent.entity.Transaction;
import carrent.util.ButtonFactory;
import carrent.util.CarComparator;
import carrent.util.ComparatorOptionA;
import carrent.util.ComparatorOptionB;

public class CarScrollPane extends JPanel{
	
	private static final long serialVersionUID = -2722529622011692794L;
	private ArrayList<CarPanel> panels;
	private ArrayList<Car> carList;
	private JScrollPane scrollPane;
	private JPanel contentPanel, sortPanel, filterPanel;
	private JCheckBox ac, sunroof, convertible, available, compact;
	public static CarPanel selectedPanel;
	private CarComparator comp;
	private JComboBox<ComparatorOptionA> cbSearchType;
	private JComboBox<ComparatorOptionB> cbAD;
	private JButton sort, rent;
	private CarPanelListener listener;
	private MainPanel parent;
	
	private Border selected, unselected;
	
	public CarScrollPane(ArrayList<Car> cars, MainPanel parent){
		super(new BorderLayout()); // Set up top level panel
		CarPanel.setCompactMode(MainPanel.compact);
		this.parent = parent;
		listener = new CarPanelListener(); // Set up listener for all action events
		
		comp = new CarComparator(); // Initialize comparator for which all sorting will be done through
		this.carList = cars;
		
		panels = new ArrayList<>(); // Panels holds all car panels in the scroll pane
		
		Collections.sort(carList, comp);
		for(Car c : carList){
			panels.add(new CarPanel(c)); // Create panels for each car given
		}
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Set up main content panel for scroll pane
		

		selected = BorderFactory.createRaisedBevelBorder(); // Panels which have borders with beveled edges to make them look vaguely
		unselected = BorderFactory.createRaisedBevelBorder();// button-like, with the selected panel looking pressed.
		
		for(CarPanel p : panels){ // Draw each panel onto the main content panel and listen for panel clicks
			p.addActionListener(listener);
			p.setBorder(unselected);
			contentPanel.add(p);
		}
		
		sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Set up panel holding combo boxes for sorting options
		sortPanel.add(new JLabel("Sort results by:"));
		
		cbSearchType = new JComboBox<>(ComparatorOptionA.values()); // Combo boxes for sort criteria 
		cbAD = new JComboBox<>(ComparatorOptionB.values());
		
		sortPanel.add(cbSearchType);
		sortPanel.add(cbAD);
		
		sort = new JButton("Go"); // "Go" button will apply sort and filter changes
		sort.setActionCommand("sort");
		sort.addActionListener(listener);
		sortPanel.add(sort);
		sortPanel.add(Box.createHorizontalGlue());
		sortPanel.add(rent = ButtonFactory.createButton("Rent Selected Car", "rent", listener));
		sortPanel.setBorder(BorderFactory.createEtchedBorder());
		
		filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Filter panel holds options for quick filtering results
		filterPanel.add(new JLabel("Only show cars with:"));
		
		JPanel compactModePanel = new JPanel();
		compact = new JCheckBox("Compact Mode", MainPanel.compact);
		compactModePanel.add(compact);
		compactModePanel.setBorder(BorderFactory.createEtchedBorder());
		
		ac = new JCheckBox("AC"); 			// filter options include ac, sunroof, convertible, and availibility
		sunroof = new JCheckBox("Sunroof"); // (basically, every boolean value)
		convertible = new JCheckBox("Convertible");
		available = new JCheckBox("Available");
		
		filterPanel.add(ac);
		filterPanel.add(sunroof);
		filterPanel.add(convertible);
		filterPanel.add(available);
		filterPanel.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel bottomRow = new JPanel(new BorderLayout());
		bottomRow.add(filterPanel, BorderLayout.CENTER);
		bottomRow.add(compactModePanel, BorderLayout.EAST);
		
		scrollPane = new JScrollPane(contentPanel); // Initialize the scrolling pane itself
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Speed up the vertical scroll (as it is very slow by default)
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel bottom = new JPanel(new GridLayout(2, 1)); // Temporary panel to group sorting and filtering panels together
		bottom.add(sortPanel);
		bottom.add(bottomRow);
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		selectedPanel = null; // The actively selected panel starts out as null
		
	}
	
	public void updateCarList(ArrayList<Car> cars){
		contentPanel.removeAll(); // Start by removing everything that was in the panel prior
		CarPanel.setCompactMode(compact.isSelected());
		carList = cars;
		Collections.sort(carList, comp); // Sort the new cars list if there were changes to sorting or filtering criteria
		panels = new ArrayList<CarPanel>();
		
		for(Car c : carList){
			panels.add(new CarPanel(c));
		}
		boolean selectedExistsAfterUpdate = false; // See if the selected panel will still be in the list of new panels to add
		for(CarPanel p : panels){
			if((ac.isSelected() && !p.getBackingCar().hasAC()) || // Automatically skip to the next car if this one fails any filter condition
			   (sunroof.isSelected() && !p.getBackingCar().isSunroof()) ||
			   (convertible.isSelected() && !p.getBackingCar().isConvertible()) ||
			   (available.isSelected() && p.getBackingCar().isRented())){ 
				continue;
			}
			if(selectedPanel != null && selectedPanel.getBackingCar().equals(p.getBackingCar())){ // If the car represented by the active panel 
				selectedPanel = p;																  // exists, pre-select it. There should only be
				p.setBorder(selected);															  // one panel per car displayed at one time.
				if(p.isCompressed()){
					p.setCompressed(false);
				}
				selectedExistsAfterUpdate = true;
			}
			else{
				p.setBorder(unselected);
			}
			p.addActionListener(listener);
			contentPanel.add(p);
		}
		if(contentPanel.getComponentCount() == 0){
			contentPanel.add(new JLabel("No results found.", SwingConstants.CENTER)); // If there are no cars after filtering, print a message to
		}																			  // the scroll panel.
		selectedPanel = selectedExistsAfterUpdate ? selectedPanel : null; // Clear the selected panel if it is not in the new set of cars
		contentPanel.revalidate(); // Panel must be revalidated after adding or removing elements
		contentPanel.repaint();
	}
	
	public void sortCarList(){
		comp.setMode(((ComparatorOptionA) cbSearchType.getSelectedItem()).getValue() * ((ComparatorOptionB) cbAD.getSelectedItem()).getValue());
		updateCarList(carList); // Change the mode of the comparator based on selected sort criteria and update the panel
	}
	
	public Car getSelectedCar(){
		if(selectedPanel == null){
			return null;
		}
		return selectedPanel.getBackingCar();
	}
	
	public void sendWindowClosedEvent(){
		rent.setEnabled(true);
	}
	
	public void sendTransaction(Transaction t){
		parent.sendTransaction(t);
	}

	public class CarPanelListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("sort")){ // Sort command corresponds to the sort/filter button
				sortCarList();
			}
			else if(e.getActionCommand().equals(CarPanel.CAR_PANEL_ACTION_COMMAND)){ // Car panel's action command means a car panel 
				for(CarPanel p : panels){											 // was selected. Update the ui to reflect that.
					if(!p.isCompressed() && CarPanel.isCompactMode()){
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
			else if(e.getActionCommand().equals("rent")){
				if(parent.requestCarRental()){
					rent.setEnabled(false);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(600, 500));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ArrayList<Car> carList = new ArrayList<>();
		StringBuilder testBLOB = new StringBuilder();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream("testcar.png"));
		int next;
		while((next = in.read()) != -1){
			testBLOB.append((char) next);
		}
		in.close();
		/*
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 1, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 1, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 2, true, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 3, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 2, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 4, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 5, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 4, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 6, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 7, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 3, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 8, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 9, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 8, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 10, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 11, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 7, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 1234, true, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 12, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 5, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 13, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 14, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 9, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 15, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 16, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 12, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 17, false, testBLOB.toString()));
		carList.add(new Car("Make", "Country", Car.randomVIN(), "Model", 18, "Size", "Type", Car.randomLicensePlate(), "Hybrid", "Automatic", 10, 5, 
				  "Color", false, 20, false, true, "Condition", 12345, 19, true, testBLOB.toString()));
				  */
		frame.add(new CarScrollPane(carList, null));
		frame.setVisible(true);
	}
	
	
	
}
