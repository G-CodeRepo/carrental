package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.util.ButtonFactory;
import carrent.util.SearchOperator;
import carrent.util.SearchRequest;

public class AdvancedSearchWindow extends JPanel implements DatabaseConstants{

	private JComboBox<String> make, country, size, type, fuel, transmission, color, ac, sunroof, convertible, available, year, door, passenger, mpg;
	private JComboBox<SearchOperator> yearOp, doorOp, passengerOp, mpgOp;
	private JPanel makeBox, countryBox, yearBox, sizeBox, typeBox, fuelBox, transmissionBox, doorBox, passengersBox, colorBox, mpgBox, acBox, sunroofBox, convertibleBox, availableBox;

	
	private JFrame frame;
	private QuickSearchPanel qsp;
	
	public AdvancedSearchWindow(JFrame frame, QuickSearchPanel qsp) {
		super.setLayout(new BorderLayout());
		
		AdvancedSearchListener listener = new AdvancedSearchListener(this);
		JPanel searchFields = new JPanel(new GridLayout(15, 1));
		this.frame = frame;
		this.qsp = qsp;
		
		makeBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> makeEnums = DBInterface.getStrEnum(CAR_MAKE, CAR_TABLE);
		makeEnums.add(0, "Any");
		make = new JComboBox<>(makeEnums.toArray(new String[makeEnums.size()]));
		makeBox.add(new JLabel("Make: ", SwingConstants.RIGHT));
		makeBox.add(make);
		makeBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(makeBox);
		
		countryBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> countryEnums = DBInterface.getStrEnum(CAR_COUNTRY, CAR_TABLE);
		countryEnums.add(0, "Any");
		country = new JComboBox<>(countryEnums.toArray(new String[countryEnums.size()]));
		countryBox.add(new JLabel("Country: ", SwingConstants.RIGHT));
		countryBox.add(country);
		countryBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(countryBox);
		
		yearBox = new JPanel(new GridLayout(1, 2));
		JPanel yearSelect = new JPanel();
		yearSelect.setLayout(new BoxLayout(yearSelect, BoxLayout.X_AXIS));
		yearOp = new JComboBox<>(SearchOperator.values());
		int yearMin = DBInterface.getMin(CAR_YEAR, CAR_TABLE);
		int yearMax = DBInterface.getMax(CAR_YEAR, CAR_TABLE);
		ArrayList<String> yearEnums = new ArrayList<String>();
		yearEnums.add("Any");
		for(int i = yearMin; i < yearMax + 1; i++) {
			yearEnums.add(i + "");
		}
		year = new JComboBox(yearEnums.toArray());
		yearBox.add(new JLabel("Year: ", SwingConstants.RIGHT));
		yearSelect.add(yearOp);
		yearSelect.add(year);
		yearBox.add(yearSelect);
		yearBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(yearBox);
		
		sizeBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> sizeEnums = DBInterface.getStrEnum(CAR_SIZE, CAR_TABLE);
		sizeEnums.add(0, "Any");
		size = new JComboBox<>(sizeEnums.toArray(new String[sizeEnums.size()]));
		sizeBox.add(new JLabel("Size: ", SwingConstants.RIGHT));
		sizeBox.add(size);
		sizeBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(sizeBox);
		
		typeBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> typeEnums = DBInterface.getStrEnum(CAR_TYPE, CAR_TABLE);
		typeEnums.add(0, "Any");
		type = new JComboBox<>(typeEnums.toArray(new String[typeEnums.size()]));
		typeBox.add(new JLabel("Type: ", SwingConstants.RIGHT));
		typeBox.add(type);
		typeBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(typeBox);
		
		fuelBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> fuelEnums = DBInterface.getStrEnum(CAR_FUEL, CAR_TABLE);
		fuelEnums.add(0, "Any");
		fuel = new JComboBox<>(fuelEnums.toArray(new String[fuelEnums.size()]));
		fuelBox.add(new JLabel("Fuel: ", SwingConstants.RIGHT));
		fuelBox.add(fuel);
		fuelBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(fuelBox);
		
		transmissionBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> transmissionEnums = DBInterface.getStrEnum(CAR_TRANSMISSION, CAR_TABLE);
		transmissionEnums.add(0, "Any");
		transmission = new JComboBox<>(transmissionEnums.toArray(new String[transmissionEnums.size()]));
		transmissionBox.add(new JLabel("Transmission: ", SwingConstants.RIGHT));
		transmissionBox.add(transmission);
		transmissionBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(transmissionBox);
		
		doorBox = new JPanel(new GridLayout(1, 2));
		JPanel doorSelect = new JPanel();
		doorSelect.setLayout(new BoxLayout(doorSelect, BoxLayout.X_AXIS));
		doorOp = new JComboBox<>(SearchOperator.values());
		int doorMin = DBInterface.getMin(CAR_DOORS, CAR_TABLE);
		int doorMax = DBInterface.getMax(CAR_DOORS, CAR_TABLE);
		ArrayList<String> doorEnums = new ArrayList<String>();
		doorEnums.add("Any");
		for(int i = doorMin; i < doorMax + 1; i++) {
			doorEnums.add(i + "");
		}
		door = new JComboBox(doorEnums.toArray());
		doorBox.add(new JLabel("Doors: ", SwingConstants.RIGHT));
		doorSelect.add(doorOp);
		doorSelect.add(door);
		doorBox.add(doorSelect);
		doorBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(doorBox);
		
		passengersBox = new JPanel(new GridLayout(1, 2));
		JPanel passengersSelect = new JPanel();
		passengersSelect.setLayout(new BoxLayout(passengersSelect, BoxLayout.X_AXIS));
		passengerOp = new JComboBox<>(SearchOperator.values());
		int passengersMin = DBInterface.getMin(CAR_PASSENGERS, CAR_TABLE);
		int passengersMax = DBInterface.getMax(CAR_PASSENGERS, CAR_TABLE);
		ArrayList<String> passengerEnums = new ArrayList<String>();
		passengerEnums.add("Any");
		for(int i = passengersMin; i < passengersMax + 1; i++) {
			passengerEnums.add(i + "");
		}
		passenger = new JComboBox(passengerEnums.toArray());
		passengersBox.add(new JLabel("Passengers: ", SwingConstants.RIGHT));
		passengersSelect.add(passengerOp);
		passengersSelect.add(passenger);
		passengersBox.add(passengersSelect);
		passengersBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(passengersBox);
		
		colorBox = new JPanel(new GridLayout(1, 2));
		ArrayList<String> colorEnums = DBInterface.getStrEnum(CAR_COLOR, CAR_TABLE);
		colorEnums.add(0, "Any");
		color = new JComboBox<>(colorEnums.toArray(new String[colorEnums.size()]));
		colorBox.add(new JLabel("Color: ", SwingConstants.RIGHT));
		colorBox.add(color);
		colorBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(colorBox);
		
		mpgBox = new JPanel(new GridLayout(1, 2));
		JPanel mpgSelect = new JPanel();
		mpgSelect.setLayout(new BoxLayout(mpgSelect, BoxLayout.X_AXIS));
		mpgOp = new JComboBox<>(SearchOperator.values());
		int mpgMin = DBInterface.getMin(CAR_MPG, CAR_TABLE);
		int mpgMax = DBInterface.getMax(CAR_MPG, CAR_TABLE);
		ArrayList<String> mpgEnums = new ArrayList<String>();
		mpgEnums.add("Any");
		for(int i = mpgMin; i < mpgMax + 1; i++) {
			mpgEnums.add(i + "");
		}
		mpg = new JComboBox(mpgEnums.toArray());
		mpgBox.add(new JLabel("MPG: ", SwingConstants.RIGHT));
		mpgSelect.add(mpgOp);
		mpgSelect.add(mpg);
		mpgBox.add(mpgSelect);
		mpgBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(mpgBox);
		String[] boolOptions = {"Any", "Yes", "No"};
		
		acBox = new JPanel(new GridLayout(1, 2));
		ac = new JComboBox<>(boolOptions);
		acBox.add(new JLabel("AC: ", SwingConstants.RIGHT));
		acBox.add(ac);
		acBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(acBox);
		
		sunroofBox = new JPanel(new GridLayout(1, 2));
		sunroof = new JComboBox<>(boolOptions);
		sunroofBox.add(new JLabel("Sunroof: ", SwingConstants.RIGHT));
		sunroofBox.add(sunroof);
		sunroofBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(sunroofBox);
		
		convertibleBox = new JPanel(new GridLayout(1, 2));
		convertible = new JComboBox<>(boolOptions);
		convertibleBox.add(new JLabel("Convertible: ", SwingConstants.RIGHT));
		convertibleBox.add(convertible);
		convertibleBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(convertibleBox);
		
		availableBox = new JPanel(new GridLayout(1, 2));
		available = new JComboBox<>(boolOptions);
		availableBox.add(new JLabel("Available: ", SwingConstants.RIGHT));
		availableBox.add(available);
		availableBox.setBorder(BorderFactory.createEtchedBorder());
		searchFields.add(availableBox);
		
		this.add(searchFields, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttons.add(ButtonFactory.createButton("Search", "search", listener));
		buttons.add(ButtonFactory.createButton("Clear", "clear", listener));
		buttons.add(ButtonFactory.createButton("Cancel", "cancel", listener));
		
		this.add(buttons, BorderLayout.SOUTH);
	}
	
	public void clear(){
		make.setSelectedIndex(0);
		country.setSelectedIndex(0);
		size.setSelectedIndex(0);
		type.setSelectedIndex(0);
		fuel.setSelectedIndex(0);
		transmission.setSelectedIndex(0);
		color.setSelectedIndex(0);
		ac.setSelectedIndex(0);
		sunroof.setSelectedIndex(0);
		convertible.setSelectedIndex(0);
		available.setSelectedIndex(0);
		year.setSelectedIndex(0);
		door.setSelectedIndex(0);
		passenger.setSelectedIndex(0);
		mpg.setSelectedIndex(0);
	}
	
	public void search(){
		SearchRequest s = new SearchRequest();
		if(make.getSelectedIndex() != 0){
			s.setMake((String) make.getSelectedItem());
		}
		if(country.getSelectedIndex() != 0){
			s.setCountry((String) country.getSelectedItem());
		}
		if(size.getSelectedIndex() != 0){
			s.setSize((String) size.getSelectedItem());
		}
		if(type.getSelectedIndex() != 0){
			s.setType((String) type.getSelectedItem());
		}
		if(fuel.getSelectedIndex() != 0){
			s.setFuel((String) fuel.getSelectedItem());
		}
		if(transmission.getSelectedIndex() != 0){
			s.setTrans((String) transmission.getSelectedItem());
		}
		if(color.getSelectedIndex() != 0){
			s.setColor((String) color.getSelectedItem());
		}
		if(ac.getSelectedIndex() != 0){
			s.setAc(ac.getSelectedIndex() == 1);
		}
		if(sunroof.getSelectedIndex() != 0){
			s.setSunroof(sunroof.getSelectedIndex() == 1);
		}
		if(convertible.getSelectedIndex() != 0){
			s.setConvertible(convertible.getSelectedIndex() == 1);
		}
		if(available.getSelectedIndex() != 0){
			s.setRented(available.getSelectedIndex() == 2);
		}
		if(year.getSelectedIndex() != 0){
			s.setYear(Integer.parseInt(year.getSelectedItem().toString()));
			s.setYearOp((SearchOperator) yearOp.getSelectedItem());		
		}
		if(door.getSelectedIndex() != 0){
			s.setDoor(Integer.parseInt(door.getSelectedItem().toString()));
			s.setDoorOp((SearchOperator) doorOp.getSelectedItem());		
		}
		if(passenger.getSelectedIndex() != 0){
			s.setPassenger(Integer.parseInt(passenger.getSelectedItem().toString()));
			s.setPassengerOp((SearchOperator) passengerOp.getSelectedItem());		
		}
		if(mpg.getSelectedIndex() != 0){
			s.setMpg(Integer.parseInt(mpg.getSelectedItem().toString()));
			s.setMpgOp((SearchOperator) mpgOp.getSelectedItem());		
		}
		qsp.sendSearch(s);
	}
	
	public static AdvancedSearchWindow createASW(QuickSearchPanel qsp){
		JFrame frame = new JFrame("Advanced Search");
		AdvancedSearchWindow asw = new AdvancedSearchWindow(frame, qsp);
		frame.setSize(new Dimension(450, 635));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.add(asw);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		return asw;
	}
	
	public class AdvancedSearchListener implements ActionListener{

		private AdvancedSearchWindow parent;
		
		public AdvancedSearchListener(AdvancedSearchWindow parent){
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
							
				case "clear" :  clear();
								break;
								
				case "search" : search();
				
				case "cancel" : if(parent.frame != null){
									parent.frame.dispose();
								}
								qsp.sendWindowClosedEvent();
								break;
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(450, 635));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DBInterface db = new DBInterface();
		frame.add(new AdvancedSearchWindow(frame, null));
		frame.setVisible(true);
		db.closeConnection();
	}
		
}
