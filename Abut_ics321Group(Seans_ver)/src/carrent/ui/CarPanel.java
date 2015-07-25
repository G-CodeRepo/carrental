package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import carrent.db.DBInterface;
import carrent.entity.Car;
import carrent.entity.Location;

public class CarPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = -1345573142658938418L;

	public static final String CAR_PANEL_ACTION_COMMAND = "CarPanelSelected";
	
	private static boolean compactMode = false;
	private static Dimension uncompressedSize = new Dimension(900, 170);
	private static Dimension compressedSize = new Dimension(900, 50);
	
	private Car car;
	private boolean compressed;
	
	private JPanel textPanel, infoBox, imagePanel, titleRow;
	private JLabel year, make, model, rented, color, type,  doors, passengers, sunroof, convertible, transmission, ac, fuel, mpg, price, location;
	
	public CarPanel(Car car){
		
		super(new BorderLayout());
		super.setBackground(Color.white);
		
		this.car = car;
		this.compressed = compactMode;
		
		ArrayList<JLabel> labels;
		
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
		textPanel.setBackground(Color.white);
		infoBox = new JPanel(new GridLayout(6, 4));
		infoBox.setBackground(Color.white);
		imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		imagePanel.setMinimumSize(new Dimension(260, 100));
		imagePanel.setPreferredSize(new Dimension(260, 100));
		imagePanel.setMaximumSize(new Dimension(260, 100));
		imagePanel.setBackground(Color.white);
		
		initializeLabels();
		
		titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleRow.setBackground(Color.white);
		
		labels = new ArrayList<JLabel>();
		
		labels.add(year);
		labels.add(make);
		labels.add(model);
		labels.add(rented);
		
		for(JLabel l : labels) {
			l.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 20));
			titleRow.add(l);
		}
		
		labels = new ArrayList<JLabel>();
		
		labels.add(new JLabel("Color: " ));
		labels.add(color);
		labels.add(new JLabel("Type: " ));
		labels.add(type);
		labels.add(new JLabel("Doors: "));
		labels.add(doors);
		labels.add(new JLabel("Sunroof: "));
		labels.add(sunroof);
		labels.add(new JLabel("Passengers: "));
		labels.add(passengers);
		labels.add(new JLabel("Convertible: "));
		labels.add(convertible);
		labels.add(new JLabel("Transmission: "));
		labels.add(transmission);
		labels.add(new JLabel("AC: "));
		labels.add(ac);
		labels.add(new JLabel("Fuel: "));
		labels.add(fuel);
		labels.add(new JLabel("Mileage: "));
		labels.add(mpg);
		labels.add(new JLabel("Rental Price: "));
		labels.add(price);
		labels.add(new JLabel("Location: "));
		labels.add(location);
		
		for(JLabel l : labels) {
			l.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.BOLD, 16));
			infoBox.add(l);
		}
		
		infoBox.setMinimumSize(new Dimension(580, 120));
		infoBox.setPreferredSize(new Dimension(580, 120));
		infoBox.setMaximumSize(new Dimension(580, 120));
		infoBox.setAlignmentX(CENTER_ALIGNMENT);
		
		this.addMouseListener(this);

		if(compressed){
			this.add(titleRow);
			this.setMaximumSize(compressedSize);
			this.setPreferredSize(compressedSize);
			this.setSize(compressedSize);
			
		}
		else{
			this.add(titleRow, BorderLayout.NORTH);
			textPanel.add(infoBox);
			if(car.getImage() != null){
				imagePanel.add(new JLabel(new ImageIcon(car.getImage())));
			}
			this.add(textPanel, BorderLayout.CENTER);
			this.add(imagePanel, BorderLayout.WEST);
			this.setMaximumSize(uncompressedSize);
			this.setPreferredSize(uncompressedSize);
			this.setSize(uncompressedSize);
			
		}
	}
	
	public void setCompressed(boolean compressed){
		this.compressed = compressed;
		textPanel.removeAll();
		imagePanel.removeAll();
		this.removeAll();
		
		if(compressed){
			this.add(titleRow);
			this.setMaximumSize(compressedSize);
			this.setPreferredSize(compressedSize);
			this.setSize(compressedSize);
		}
		else{
			this.add(titleRow, BorderLayout.NORTH);
			textPanel.add(infoBox);
			if(car.getImage() != null){
				imagePanel.add(new JLabel(new ImageIcon(car.getImage())));
			}
			this.add(textPanel, BorderLayout.CENTER);
			this.add(imagePanel, BorderLayout.WEST);
			this.setMaximumSize(uncompressedSize);
			this.setPreferredSize(uncompressedSize);
			this.setSize(uncompressedSize);
		}
		
		this.revalidate();
	}
	
	private void initializeLabels(){
		Location carLocation = DBInterface.getLocation(car.getLocationID());
		
		year = new JLabel("<html><b>"+ car.getYear() + "</b></html>");
		make = new JLabel("<html><b>" + car.getMake() + "</b></html>");
		model = new JLabel("<html><b>" + car.getModel() + " - </b></html>");
		type = new JLabel("<html><b>" + car.getBodyType()+ "</b></html>");
		color = new JLabel("<html><b>" + car.getColor()+ "</b></html>");
		
		if(car.isRented()){
			rented = new JLabel("<html><font color=red><b>Unavailable</b></color></html>");
		}
		else{
			rented = new JLabel("<html><font color=green><b>Available</b></color></html>");
		}
		
		doors = new JLabel("<html><b>" + car.getDoor() + "</b></html>");
		passengers = new JLabel("<html> <b>" + car.getPassenger() + "</b></html>");
		
		if(car.isSunroof()){
			sunroof = new JLabel("<html><font color=green><b>Yes</b></html>");
		}
		else{
			sunroof = new JLabel("<html><font color=red><b>No</b></color></html>");
		}
		
		if(car.isConvertible()){
			convertible = new JLabel("<html><font color=green><b>Yes</b></color></html>");
		}
		else{
			convertible = new JLabel("<html><font color=red><b>No</b></color></html>");
		}
		
		transmission = new JLabel("<html><b>" + car.getTransmission()+ "</b></color></html>");
		
		if(car.hasAC()){
			ac = new JLabel("<html>  <font color=green><b>Yes</b></color></html>");
		}
		else{
			ac = new JLabel("<html>  <font color=red><b>No</b></color></html>");
		}
		
		fuel = new JLabel("<html><b>" + car.getFuel() + "</b></html>");
		mpg = new JLabel("<html><b>" + car.getMPG() + " MPG</b></html>");
		price = new JLabel("<html><b>$" + car.getRentalPrice() + " per day</b></html>");
		location = new JLabel("<html> <b>" + carLocation.getCity() + ", " + carLocation.getState() + "</b></html>");
		
	}
	
	public Car getBackingCar(){
		return this.car;
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
			ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CAR_PANEL_ACTION_COMMAND);
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
		testFrame.setSize(new Dimension(500, 160));
		
		StringBuilder testBLOB = new StringBuilder();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream("testcar.png"));
		int next;
		while((next = in.read()) != -1){
			testBLOB.append((char) next);
		}
		in.close();
		Car car = new Car("Make", "Country", "VIN", "Model", 1234, "Size", "Type", "Plate", "Hybrid", "Automatic", 4, 5, 
						  "Color", false, 20, false, true, "Condition", 12345, 1234, false, 1, testBLOB.toString());
		
		testFrame.add(new CarPanel(car));
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setVisible(true);
	}
	
}


