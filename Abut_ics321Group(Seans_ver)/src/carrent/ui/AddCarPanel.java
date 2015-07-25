package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;


import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.entity.Car;

public class AddCarPanel extends JPanel implements DatabaseConstants{

	private AutoSuggest make, country, type, model, fuel, transmission, color, size;
	private JCheckBox ac, sunroof, convertible, rented;
	private JTextField vin, plate, condition, msrp, rentalprice, locationID, year;
	private JSpinner doors, passengers, mpg;
	private Blob image;
	private File file;
	private JButton addCar, openFile;
	private JFileChooser imageChooser;
	private JLabel selectedImage, notice;
	private AddCarPanelListener listener;
	
	public AddCarPanel() {
		this.setLayout(new BorderLayout());
		//this.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		//this.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		//this.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
		
		listener = new AddCarPanelListener();
		
		imageChooser = new JFileChooser();
		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		imageChooser.addChoosableFileFilter(imageFilter);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		JPanel textFields1 = new JPanel(new GridLayout(10, 1));
		JPanel textFields2 = new JPanel(new GridLayout(8, 1));
		JPanel textFields3 = new JPanel();
		textFields3.setLayout(new BoxLayout(textFields3, BoxLayout.Y_AXIS));
		
		JPanel makeGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		makeGroup.add(new JLabel("Make:"));
		make = new AutoSuggest(DBInterface.getStrEnum(CAR_MAKE, CAR_TABLE));
		makeGroup.add(make);
		textFields1.add(makeGroup);
		
		JPanel countryGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		countryGroup.add(new JLabel("Country:"));
		country = new AutoSuggest(DBInterface.getStrEnum(CAR_COUNTRY, CAR_TABLE));
		countryGroup.add(country);
		textFields1.add(countryGroup);
		
		JPanel vinGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		vinGroup.add(new JLabel("VIN:"));
		vin = new JTextField(14);
		vinGroup.add(vin);
		vinGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		vinGroup.setPreferredSize(new Dimension(200, 40));
		textFields1.add(vinGroup);
		
		JPanel modelGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		modelGroup.add(new JLabel("Model:"));
		model = new AutoSuggest(DBInterface.getStrEnum(CAR_MODEL, CAR_TABLE));
		modelGroup.add(model);
		textFields1.add(modelGroup);
		
		JPanel colorGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		colorGroup.add(new JLabel("Color:"));
		color = new AutoSuggest(DBInterface.getStrEnum(CAR_COLOR, CAR_TABLE));
		colorGroup.add(color);
		textFields1.add(colorGroup);
		
		JPanel sizeGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sizeGroup.add(new JLabel("Size:"));
		size = new AutoSuggest(DBInterface.getStrEnum(CAR_SIZE, CAR_TABLE));
		sizeGroup.add(size);
		textFields1.add(sizeGroup);
		
		JPanel typeGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		typeGroup.add(new JLabel("Type:"));
		type = new AutoSuggest(DBInterface.getStrEnum(CAR_TYPE, CAR_TABLE));
		typeGroup.add(type);
		textFields1.add(typeGroup);
		
		JPanel plateGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		plateGroup.add(new JLabel("Plate:"));
		plate = new JTextField(14);
		plateGroup.add(plate);
		plateGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		plateGroup.setPreferredSize(new Dimension(200, 40));
		textFields1.add(plateGroup);
		
		JPanel fuelGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		fuelGroup.add(new JLabel("Fuel:"));
		fuel = new AutoSuggest(DBInterface.getStrEnum(CAR_TYPE, CAR_TABLE));
		fuelGroup.add(fuel);
		textFields1.add(fuelGroup);
		
		JPanel transmissionGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		transmissionGroup.add(new JLabel("Transmission:"));
		transmission = new AutoSuggest(DBInterface.getStrEnum(CAR_TRANSMISSION, CAR_TABLE));
		transmissionGroup.add(transmission);
		textFields1.add(transmissionGroup);
		
		JPanel doorsGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		doorsGroup.add(new JLabel("Doors"));
		doors = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		doorsGroup.add(doors);
		//doorsGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//doorsGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(doorsGroup);
		
		JPanel passengersGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passengersGroup.add(new JLabel("Passengers:"));
		passengers = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		passengersGroup.add(passengers);
		//passengersGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//passengersGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(passengersGroup);
		
		JPanel mpgGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		mpgGroup.add(new JLabel("MPG"));
		mpg = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		mpgGroup.add(mpg);
		//mpgGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//mpgGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(mpgGroup);
		
		JPanel yearGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		yearGroup.add(new JLabel("Year:"));
		year = new JTextField(14);
		yearGroup.add(year);
		//yearGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//yearGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(yearGroup);
		
		JPanel conditionGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		conditionGroup.add(new JLabel("Condition:"));
		condition = new JTextField(14);
		conditionGroup.add(condition);
		//conditionGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//conditionGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(conditionGroup);
		
		JPanel msrpGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		msrpGroup.add(new JLabel("MSRP:"));
		msrp = new JTextField(14);
		msrpGroup.add(msrp);
		//msrpGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//msrpGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(msrpGroup);
		
		JPanel rentalpriceGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		rentalpriceGroup.add(new JLabel("Rental Price:"));
		rentalprice = new JTextField(14);
		rentalpriceGroup.add(rentalprice);
		//rentalpriceGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//rentalpriceGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(rentalpriceGroup);
		
		JPanel locationGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		locationGroup.add(new JLabel("Location:"));
		locationID = new JTextField(14);
		locationGroup.add(locationID);
		//locationGroup.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//locationGroup.setPreferredSize(new Dimension(200, 40));
		textFields2.add(locationGroup);
		
		JPanel booleanGroup = new JPanel();
		booleanGroup.setMaximumSize(new Dimension(150, 200));
		booleanGroup.setLayout(new BoxLayout(booleanGroup, BoxLayout.Y_AXIS));
		ac = new JCheckBox();
		sunroof = new JCheckBox();
		convertible = new JCheckBox();
		rented = new JCheckBox();
		
		JPanel acGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		acGroup.add(new JLabel("AC: "));
		acGroup.add(ac);
		//acGroup.setBorder(BorderFactory.createRaisedBevelBorder());
		booleanGroup.add(acGroup);
		
		JPanel sunroofGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sunroofGroup.add(new JLabel("Sunroof: "));
		sunroofGroup.add(sunroof);
		//sunroofGroup.setBorder(BorderFactory.createRaisedBevelBorder());
		booleanGroup.add(sunroofGroup);
		
		JPanel convertibleGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		convertibleGroup.add(new JLabel("Convertible: "));
		convertibleGroup.add(convertible);
		//convertibleGroup.setBorder(BorderFactory.createRaisedBevelBorder());
		booleanGroup.add(convertibleGroup);
		//textFields3.add(acGroup);
		//textFields3.add(sunroofGroup);
		//textFields3.add(convertibleGroup);
		textFields3.add(booleanGroup);
		
		JPanel rentedGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		rentedGroup.add(new JLabel("Rented: "));
		rentedGroup.add(rented);
		rented.setEnabled(false);
		// convertibleGroup.setBorder(BorderFactory.createRaisedBevelBorder());
		booleanGroup.add(rentedGroup);
		
		JPanel imageGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		imageGroup.add(new JLabel("Image:"));
		selectedImage = new JLabel(" ");
		openFile = new JButton("Open File");
		openFile.addActionListener(listener);
		openFile.setActionCommand("openFile");
		imageGroup.add(openFile);
		imageGroup.add(selectedImage);
		textFields3.add(imageGroup);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		notice = new JLabel(" ");
		
		addCar = new JButton("Add Car");
		addCar.setActionCommand("addCar");
		addCar.addActionListener(listener);
		buttonPanel.add(addCar);
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
	
	public Blob processImage(File f) {
		byte[] bytes = new byte[0];
		try{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			StringBuilder sb = new StringBuilder();
			int nextI;
			while((nextI = in.read()) != -1){
				sb.append((char) nextI);
			}
			in.close();
			bytes = new byte[sb.toString().length()];
			for(int j = 0; j < bytes.length; j++){
				bytes[j] = (byte) sb.toString().charAt(j);
			}
		}
		catch(IOException e){
			System.err.println("Bad image path for " + make + " " + model);	
		}
		
		
		SerialBlob imageBlob = null;
		try {
			imageBlob = new SerialBlob(bytes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return imageBlob;
	}
	
	public class AddCarPanelListener implements ActionListener{

		
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()){
						
				
					case "openFile" : int returnVal = imageChooser.showOpenDialog(AddCarPanel.this);
									  if (returnVal == JFileChooser.APPROVE_OPTION) {
										  	file = imageChooser.getSelectedFile();
										  	if((image = processImage(file)) != null) {
										  		selectedImage.setText(imageChooser.getSelectedFile().getName());
										  		return;
										  	} else {
										  		selectedImage.setText("Image not valid");
										  	}
									  }
									  break;
					case "addCar" : Car c = new Car(make.textField.getText(),country.textField.getText(),  vin.getText(),model.textField.getText(), 
											Integer.parseInt(year.getText()), size.textField.getText(), type.textField.getText(), plate.getText(), 
											fuel.textField.getText(),  transmission.textField.getText(), (int) doors.getValue(), (int) passengers.getValue(), 
											color.textField.getText(), ac.isSelected(), (int) mpg.getValue(), sunroof.isSelected(), convertible.isSelected(), 
											condition.getText(), Integer.parseInt(msrp.getText()), Integer.parseInt(rentalprice.getText()), false,
											Integer.parseInt(locationID.getText()), image);
					
									int result = DBInterface.addCar(c);
									if(result != 0) {
										notice.setText("Unable to add Car");
									} else {
										notice.setText("Car added sucessfully");
									}
									break;
									
								
			}
		
		}
	}
}
