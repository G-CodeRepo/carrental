package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;


import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.entity.Car;

public class ModifyCarPanel extends JPanel implements DatabaseConstants{

	private AutoSuggest make, country, type, model, fuel, transmission, color, size;
	private JCheckBox ac, sunroof, convertible, rented;
	private JTextField vin, plate, condition, msrp, rentalprice, locationID, year;
	private JSpinner doors, passengers, mpg;
	private JButton editCar, openFile;
	private JLabel notice, selectedImage;
	private ModifyCarPanelListener listener;
	private Car c;
	
	public ModifyCarPanel(Car car) {
		this.setLayout(new BorderLayout());
		//this.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		//this.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		//this.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
		
		this.c = car;
		listener = new ModifyCarPanelListener();
		
		if (c != null) {

			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
			JPanel textFields1 = new JPanel(new GridLayout(10, 1));
			JPanel textFields2 = new JPanel(new GridLayout(8, 1));
			JPanel textFields3 = new JPanel();
			textFields3.setLayout(new BoxLayout(textFields3, BoxLayout.Y_AXIS));

			JPanel makeGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			makeGroup.add(new JLabel("Make:"));
			make = new AutoSuggest(DBInterface.getStrEnum(CAR_MAKE, CAR_TABLE));
			make.textField.setText(c.getMake());
			makeGroup.add(make);
			textFields1.add(makeGroup);

			JPanel countryGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			countryGroup.add(new JLabel("Country:"));
			country = new AutoSuggest(DBInterface.getStrEnum(CAR_COUNTRY,
					CAR_TABLE));
			country.textField.setText(c.getCountry());
			countryGroup.add(country);
			textFields1.add(countryGroup);

			JPanel vinGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			vinGroup.add(new JLabel("VIN:"));
			vin = new JTextField(14);
			vin.setText(c.getVIN());
			vin.setEditable(false);
			vinGroup.add(vin);
			vinGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			vinGroup.setPreferredSize(new Dimension(200, 40));
			textFields1.add(vinGroup);

			JPanel modelGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			modelGroup.add(new JLabel("Model:"));
			model = new AutoSuggest(
					DBInterface.getStrEnum(CAR_MODEL, CAR_TABLE));
			model.textField.setText(c.getModel());
			modelGroup.add(model);
			textFields1.add(modelGroup);

			JPanel colorGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			colorGroup.add(new JLabel("Color:"));
			color = new AutoSuggest(
					DBInterface.getStrEnum(CAR_COLOR, CAR_TABLE));
			color.textField.setText(c.getColor());
			colorGroup.add(color);
			textFields1.add(colorGroup);

			JPanel sizeGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			sizeGroup.add(new JLabel("Size:"));
			size = new AutoSuggest(DBInterface.getStrEnum(CAR_SIZE, CAR_TABLE));
			size.textField.setText(c.getSize());
			sizeGroup.add(size);
			textFields1.add(sizeGroup);

			JPanel typeGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			typeGroup.add(new JLabel("Type:"));
			type = new AutoSuggest(DBInterface.getStrEnum(CAR_TYPE, CAR_TABLE));
			type.textField.setText(c.getBodyType());
			typeGroup.add(type);
			textFields1.add(typeGroup);

			JPanel plateGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			plateGroup.add(new JLabel("Plate:"));
			plate = new JTextField(14);
			plate.setText(c.getPlate());
			plateGroup.add(plate);
			plateGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			plateGroup.setPreferredSize(new Dimension(200, 40));
			textFields1.add(plateGroup);

			JPanel fuelGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			fuelGroup.add(new JLabel("Fuel:"));
			fuel = new AutoSuggest(DBInterface.getStrEnum(CAR_TYPE, CAR_TABLE));
			fuel.textField.setText(c.getFuel());
			fuelGroup.add(fuel);
			textFields1.add(fuelGroup);

			JPanel transmissionGroup = new JPanel(new FlowLayout(
					FlowLayout.RIGHT));
			transmissionGroup.add(new JLabel("Transmission:"));
			transmission = new AutoSuggest(DBInterface.getStrEnum(
					CAR_TRANSMISSION, CAR_TABLE));
			transmission.textField.setText(c.getTransmission());
			transmissionGroup.add(transmission);
			textFields1.add(transmissionGroup);

			JPanel doorsGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			doorsGroup.add(new JLabel("Doors"));
			doors = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
			doors.setValue(c.getDoor());
			doorsGroup.add(doors);
			// doorsGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,
			// 5));
			// doorsGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(doorsGroup);

			JPanel passengersGroup = new JPanel(
					new FlowLayout(FlowLayout.RIGHT));
			passengersGroup.add(new JLabel("Passengers:"));
			passengers = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
			passengers.setValue(c.getPassenger());
			passengersGroup.add(passengers);
			// passengersGroup.setBorder(BorderFactory.createEmptyBorder(5, 5,
			// 5, 5));
			// passengersGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(passengersGroup);

			JPanel mpgGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			mpgGroup.add(new JLabel("MPG"));
			mpg = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
			mpg.setValue(c.getMPG());
			mpgGroup.add(mpg);
			// mpgGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			// mpgGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(mpgGroup);

			JPanel yearGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			yearGroup.add(new JLabel("Year:"));
			year = new JTextField(14);
			year.setText("" + c.getYear());
			yearGroup.add(year);
			// yearGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			// yearGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(yearGroup);

			JPanel conditionGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			conditionGroup.add(new JLabel("Condition:"));
			condition = new JTextField(14);
			condition.setText(c.getCondition());
			conditionGroup.add(condition);
			// conditionGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,
			// 5));
			// conditionGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(conditionGroup);

			JPanel msrpGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			msrpGroup.add(new JLabel("MSRP:"));
			msrp = new JTextField(14);
			msrp.setText("" + c.getMSRP());
			msrpGroup.add(msrp);
			// msrpGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			// msrpGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(msrpGroup);

			JPanel rentalpriceGroup = new JPanel(new FlowLayout(
					FlowLayout.RIGHT));
			rentalpriceGroup.add(new JLabel("Rental Price:"));
			rentalprice = new JTextField(14);
			rentalprice.setText("" + c.getRentalPrice());
			rentalpriceGroup.add(rentalprice);
			// rentalpriceGroup.setBorder(BorderFactory.createEmptyBorder(5, 5,
			// 5, 5));
			// rentalpriceGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(rentalpriceGroup);

			JPanel locationGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			locationGroup.add(new JLabel("Location:"));
			locationID = new JTextField(14);
			locationID.setText("" + c.getLocationID());
			locationGroup.add(locationID);
			// locationGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,
			// 5));
			// locationGroup.setPreferredSize(new Dimension(200, 40));
			textFields2.add(locationGroup);

			JPanel booleanGroup = new JPanel();
			booleanGroup.setMaximumSize(new Dimension(150, 200));
			booleanGroup
					.setLayout(new BoxLayout(booleanGroup, BoxLayout.Y_AXIS));
			ac = new JCheckBox();
			if (c.hasAC())
				ac.setSelected(true);
			sunroof = new JCheckBox();
			if (c.isSunroof())
				sunroof.setSelected(true);
			convertible = new JCheckBox();
			if (c.isConvertible())
				convertible.setSelected(true);
			rented = new JCheckBox();
			if (c.isRented())
				rented.setSelected(true);

			JPanel acGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			acGroup.add(new JLabel("AC: "));
			acGroup.add(ac);
			// acGroup.setBorder(BorderFactory.createRaisedBevelBorder());
			booleanGroup.add(acGroup);

			JPanel sunroofGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			sunroofGroup.add(new JLabel("Sunroof: "));
			sunroofGroup.add(sunroof);
			// sunroofGroup.setBorder(BorderFactory.createRaisedBevelBorder());
			booleanGroup.add(sunroofGroup);

			JPanel convertibleGroup = new JPanel(new FlowLayout(
					FlowLayout.RIGHT));
			convertibleGroup.add(new JLabel("Convertible: "));
			convertibleGroup.add(convertible);
			// convertibleGroup.setBorder(BorderFactory.createRaisedBevelBorder());
			booleanGroup.add(convertibleGroup);

			JPanel rentedGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			rentedGroup.add(new JLabel("Rented: "));
			rentedGroup.add(rented);
			// convertibleGroup.setBorder(BorderFactory.createRaisedBevelBorder());
			booleanGroup.add(rentedGroup);

			// textFields3.add(acGroup);
			// textFields3.add(sunroofGroup);
			// textFields3.add(convertibleGroup);
			textFields3.add(booleanGroup);
			
			JPanel imageGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			imageGroup.add(new JLabel("Image:"));
			selectedImage = new JLabel(" ");
			openFile = new JButton("Open File");
			openFile.setEnabled(false);
			openFile.addActionListener(listener);
			openFile.setActionCommand("openFile");
			imageGroup.add(openFile);
			imageGroup.add(selectedImage);
			textFields3.add(imageGroup);
			
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			notice = new JLabel(" ");

			editCar = new JButton("Save Changes to Car");
			editCar.setActionCommand("editCar");
			editCar.addActionListener(listener);
			buttonPanel.add(editCar);
			buttonPanel.add(notice);

			mainPanel.add(textFields1);
			mainPanel.add(textFields2);
			mainPanel.add(textFields3);
			mainPanel.setMaximumSize(new Dimension(400, 600));
			mainPanel.setMinimumSize(new Dimension(400, 600));
			mainPanel.setPreferredSize(new Dimension(400, 600));
			this.add(mainPanel, BorderLayout.CENTER);
			this.add(buttonPanel, BorderLayout.SOUTH);
		}
		else {
			JPanel panel = new JPanel(false);
			JLabel filler = new JLabel("No Car Selected");
			filler.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 15));
	        filler.setHorizontalAlignment(JLabel.CENTER);
	        panel.setLayout(new GridLayout(1, 1));
	        panel.add(filler);
			this.add(panel, BorderLayout.CENTER);
		}
	}
	

	
	public class ModifyCarPanelListener implements ActionListener{

		
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()){
					case "editCar" :  c.setMake(make.textField.getText());
									  c.setCountry(country.textField.getText());
									  c.setModel(model.textField.getText()); 
									  c.setYear(Integer.parseInt(year.getText())); 
									  c.setSize(size.textField.getText()); 
									  c.setType(type.textField.getText());
									  c.setPlate(plate.getText()); 
									  c.setFuel(fuel.textField.getText());
									  c.setTrans(transmission.textField.getText()); 
									  c.setDoor((int) doors.getValue());
									  c.setPassenger((int) passengers.getValue());
									  c.setColor(color.textField.getText()); 
									  c.setAc(ac.isSelected()); 
									  c.setMpg((int) mpg.getValue()); 
									  c.setSunroof(sunroof.isSelected()); 
									  c.setConvertible(convertible.isSelected()); 
									  c.setCondition(condition.getText());
									  c.setMsrp(Integer.parseInt(msrp.getText())); 
									  c.setRentalPrice(Integer.parseInt(rentalprice.getText())); 
									  c.setRented(rented.isSelected());
									  c.setLocationID(Integer.parseInt(locationID.getText()));
					
									int result = DBInterface.updateCar(c);
									if(result != 0) {
										notice.setText("Unable to modify Car.");
									} else {
										notice.setText("Changes Saved");
									}
									break;
									
								
			}
		
		}
	}
}
