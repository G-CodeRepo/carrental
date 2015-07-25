package carrent.entity;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

/*
 * Change log:
 *
 * 		Ver.2 contains an improved print options and added method to return an arraylist of the car specs
 *
 *  	Version 2.1: - Added (but not fully implemented) rentalPrice field for consistency with database. 
 *  				 - Added static methods to randomly generate VIN numbers and license plates for 
 * 					   testing purposes.
 *
 * 		Ver.2.5: 
 * 				1) added a getter method for the rental price field
 * 				2) changed msrp and rentalPrice type into doubles for dollar values
 * 				3) changed return type of the msrp method to double
 * 				4) updated the print statements to include the new rental price field
 * 				5) added a toString version of printInfo that returns a string instead of printing to screen
 *
 *		Version 2.6: - Spec array list no longer an instance variable, it is now created when the list is requested.
 *				     - MSRP and rental price changed to integers
 *					 - Added new boolean isRented for consistency with database
 *					 - Info printing methods updated to reflect changes to msrp and rental price
 *					   (rented boolean still needs to be added to these methods)
 *
 *		Ver.2.7: created an arraylist of car fields of type object
 *				 the objects that use to be type strings "yes" and "no" are now just boolean values true and false which will be converted to
 *				 1s and 0s when needed
 *
 *      Ver.2.7: still version 2.7. as you can see, all  I did was change the version number on the author portion and fixed the comments for
 *               the toSpecObjectArray() method because it originally had 2 "String condition" instead of just one which will cause confusion and eventual
 *               bugs in code. the comments don't affect anything that is already implementing this method but just didn't want to confuse anybody.
 *               
 *		Version 2.8: - Added new parameter to constructor to accept BLOBs that are converted to images. 
 *					   This deprecates the class BLOBInputStream.
 *					 - Added a new method which returns a map containing strings (as the name of the variables) pointing
 *					   to the fields themselves. This method deprecates the previous 2 array list methods.
 *					 - Added missing getter method for year.
 *					 - Fixed a bug in which calling convertible() causes a stack overflow.
 *					 - Implemented equals and hashCode methods
 *					 - Removed redundant string conversions in toSpecArray
 *
 *		Version 2.9: - Image Blob is now handled by a Blob object.
 *					 - getFieldMap is no longer supported.
 *					 - Names of getter methods have been normalized.
 *
 */

/**
 * Car class holds the data for a single car to be put into an arraylist of cars
 
 * @author team G.A.S		// change this if you want lol
 * @version 2.9
 *
 */
public class Car {
	private String make;
	private String country;
	private String vin;
	private String model;
	private int year;
	private String size;
	private String type;
	private String plate;
	private String fuel;
	private String trans;
	private int door;
	private int passenger;
	private String color; 
	private boolean ac;
	private int mpg;
	private boolean sunroof;
	private boolean convertible;
	private String condition;
	private int msrp;
	private int rentalPrice;
	private boolean rented;
	private int locationID;
	private Blob imageBlob;
	private String imageString;
	
	private Image image;
	
	public Car(String make, String country, String vin , String model, int year, String size, String type, String plate, 
			String fuel, String trans, int door, int passenger, String color, 
			boolean ac, int mpg, boolean sunroof, boolean convertible, String condition, int msrp, int rentalPrice, boolean rented, int locationID, String imageString) {
		this.make = make;
		this.country = country;
		this.vin = vin;
		this.model = model;
		this.year = year;
		this.size = size;
		this.type = type;
		this.plate = plate;
		this.fuel = fuel;
		this.trans = trans;
		this.door = door;
		this.passenger = passenger;
		this.color = color; 
		this.ac = ac;
		this.mpg = mpg;
		this.sunroof = sunroof;
		this.convertible = convertible;
		this.condition = condition;
		this.msrp = msrp;
		this.rentalPrice = rentalPrice;
		this.rented = rented;
		this.locationID = locationID;
		this.imageBlob = null;
		this.imageString = imageString;
		
		byte[] imageBytes = new byte[imageString.length()];
		for(int i = 0; i < imageBytes.length; i++){
			imageBytes[i] = (byte) imageString.charAt(i);
		}
		
		try{
			image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		}
		catch(IOException e){
			System.err.println("ERROR - Image for car \"" + vin + "\" failed to load.");
			image = null;
		}
	}
	
	public Car(String make, String country, String vin , String model, int year, String size, String type, String plate, 
			String fuel, String trans, int door, int passenger, String color, 
			boolean ac, int mpg, boolean sunroof, boolean convertible, String condition, int msrp, int rentalPrice, boolean rented, int locationID, Blob imageBlob) {
		this.make = make;
		this.country = country;
		this.vin = vin;
		this.model = model;
		this.year = year;
		this.size = size;
		this.type = type;
		this.plate = plate;
		this.fuel = fuel;
		this.trans = trans;
		this.door = door;
		this.passenger = passenger;
		this.color = color; 
		this.ac = ac;
		this.mpg = mpg;
		this.sunroof = sunroof;
		this.convertible = convertible;
		this.condition = condition;
		this.msrp = msrp;
		this.rentalPrice = rentalPrice;
		this.rented = rented;
		this.locationID = locationID;
		this.imageBlob = imageBlob;
		this.imageString = null;
		
		try{
			image = ImageIO.read(imageBlob.getBinaryStream());
		}
		catch(IOException | SQLException e){
			System.err.println("ERROR - Image for car \"" + vin + "\" failed to load.");
			image = null;
		}
	}	
	
	/**
	 * make of car
	 * @return String
	 */
	public String getMake() {
		return this.make;
	}
	
	/**
	 * country of car company
	 * @return String
	 */
	public String getCountry() {
		return this.country;
	}
	
	/**
	 * unique Vehicle Identification Number (17 combined digits and characters)
	 * @return String
	 */
	public String getVIN() {
		return this.vin;
	}
	
	/**
	 * vehicle model name
	 * @return String
	 */
	public String getModel() {
		return this.model;
	}
	
	/**
	 * size name of vehicle (e.g. "full", "mid", etc)
	 * @return String
	 */
	public String getSize() {
		return this.size;
	}
	
	/**
	 * body type of vehicle
	 * @return String
	 */
	public String getBodyType() {
		return this.type;
	}
	
	/**
	 * license plate 
	 * @return String
	 */
	public String getPlate() {
		return this.plate;
	}
	
	/**
	 * fuel type of vehicle (e.g. "gas", "hybrid", etc.)
	 * @return String
	 */
	public String getFuel() {
		return this.fuel;
	}
	
	/**
	 * transmission type (e.g "manual", "automatic");
	 * @return String
	 */
	public String getTransmission() {
		return this.trans;
	}
	
	public int getYear(){
		return this.year;
	}
	
	/**
	 * number of doors
	 * @return int
	 */
	public int getDoor() {
		return this.door;
	}
	
	/**
	 * number of passengers
	 * @return int
	 */
	public int getPassenger() {
		return this.passenger;
	}
	
	/**
	 * vehicle color
	 * @return String
	 */
	public String getColor() {
		return this.color;
	}
	
	/**
	 * air-conditioned or not
	 * @return boolean
	 */
	public boolean hasAC() {
		return this.ac;
	}
	
	
	public int getMPG() {
		return this.mpg;
	}
	/**
	 * has sunroof or not
	 * @return boolean
	 */
	public boolean isSunroof() {
		return this.sunroof;
	}
	
	/**
	 * is a convertible or not
	 * @return boolean
	 */
	public boolean isConvertible() {
		return this.convertible; 
	}
	
	/**
	 * condition of vehicle (e.g. "new");
	 * @return String
	 */
	public String getCondition() {
		return this.condition;
	}
	
	/**
	 * manufacturer's suggested retail price of vehicle
	 * @return float
	 */
	public int getMSRP() {
		return this.msrp;
	}
	
	public int getRentalPrice() {
		return this.rentalPrice;
	}
	
	public boolean isRented() {
		return this.rented;
	}
	
	public int getLocationID() {
		return this.locationID;
	}
	public Image getImage() {
		return this.image;
	}
	
	public Blob getImageBlob(){
		return this.imageBlob;
	}
	
	public String getImageString(){
		return this.imageString;
	}
	
	/**
	 * print out every info of the car in question
	 * has two types of print options
	 * if printType = 0: prints out a vertical list
	 * if printType = 1; prints out a horizontal list 
	 * any other value will throw an IllegalArgumentException
	 * @param printType
	 */
	public void printInfo(int printType) {
		String airCon = ""; 
		String sRoof = "";
		String convert = "";
		String i = "%-20s";	// formatted to left justified with 20 characters
		if (this.ac) {
			airCon = "yes";
		} else {
			airCon = "no";
		}
		if (this.sunroof) {
			sRoof = "yes";
		} else {
			sRoof = "no";
		}
		if (this.convertible){
			convert = "yes";
		} else {
			convert = "no";
		}

		if (printType == 0) {
			System.out.printf(i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + 
							  i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + 
							  i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" +
							  i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i +" \n" +
							  i + i + "\n",
							  "Make", make, "Country", country, "Vin", vin, "Model", model, "Year", year,"Size", 
							  size,"BodyType", type, "License Plate", plate, "Fuel Type", fuel, "Transmission Type", trans,
							  "Doors", door,"Passengers", passenger, "Color", color, "A/C", airCon, "MPG", mpg, 
							  "Sunroof", sRoof,"Convertible", convert, "Condition", condition, "MSRP", 
							  msrp, "RentalPrice", rentalPrice, "Location ID", locationID); 

		} else if (printType == 1) {		
			System.out.printf(i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + "\n" +
							  i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + "\n",
							  "Make", "Country", "Vin", "Model", "Year", "Size", "BodyType", "License Plate", "Fuel Type", "Transmission Type", "Doors", 
							  "Passengers", "Color", "A/C", "MPG", "Sunroof", "Convertible", "Condition", "MSRP", "Rental Price", "Location ID", 
							  make, country, vin, model, year, size,
							  type, plate, fuel, trans, door, 
							  passenger, color, airCon, mpg, sRoof,  
							  convert, condition, msrp, rentalPrice, locationID);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * a toString() version of printInfo. returns a string instead of printing out to screen
	 * doesn't end with an new line so use System.out.println();
	 * print out every info of the car in question
	 * has two types of print options
	 * if printType = 0: prints out a vertical list
	 * if printType = 1; prints out a horizontal list 
	 * any other value will throw an IllegalArgumentException
	 * @param printType
	 */
	public String infoToString(int printType) {
		String airCon = ""; 
		String sRoof = "";
		String convert = "";
		String i = "%-20s";	// formatted to left justified with 20 characters
		if (this.ac) {
			airCon = "yes";
		} else {
			airCon = "no";
		}
		if (this.sunroof) {
			sRoof = "yes";
		} else {
			sRoof = "no";
		}
		if (this.convertible){
			convert = "yes";
		} else {
			convert = "no";
		}
		if (printType == 0) {
			return String.format(i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + 
							  	 i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + 
							     i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" +
							     i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i + "\n" + i + i  +"\n" + 
							     i + i, 
							     "Make", make, "Country", country, "Vin", vin, "Model", model, "Year", year,"Size", 
							     size,"BodyType", type, "License Plate", plate, "Fuel Type", fuel, "Transmission Type", trans,
							     "Doors", door,"Passengers", passenger, "Color", color, "A/C", airCon, "MPG", mpg, 
							     "Sunroof", sRoof,"Convertible", convert, "Condition", condition, "MSRP", 
							     msrp, "RentalPrice", rentalPrice,"Location ID", locationID);
		} else if (printType == 1) {		
			return String.format(i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + "\n" +
							     i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i,
							     "Make", "Country", "Vin", "Model", "Year", "Size", "BodyType", "License Plate", "Fuel Type", "Transmission Type", "Doors", 
							     "Passengers", "Color", "A/C", "MPG", "Sunroof", "Convertible", "Condition", "MSRP", "Rental Price", "Location ID",
							     make, country, vin, model, year, size,
							     type, plate, fuel, trans, door, 
							     passenger, color, airCon, mpg, sRoof,  
							     convert, condition, msrp, rentalPrice, locationID);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	
	
	/**
	 * toSpecArray returns an ArrayList of string elements containing the car's spec
	 * 
	 * @return ArrayList<String>
	 * @deprecated As of version 2.7, replaced by {@link Car#toSpecObjectArray() toSpecObjectArray()}
	 */
	public ArrayList<String> toSpecArray() {
		ArrayList<String> specList = new ArrayList<String>();
		specList.add(this.make);
		specList.add(this.country);
		specList.add(this.vin);
		specList.add(this.model);
		specList.add(String.valueOf(this.year));
		specList.add(this.size);
		specList.add(this.type);
		specList.add(this.plate);
		specList.add(this.fuel);
		specList.add(this.trans);
		specList.add(String.valueOf(this.door));
		specList.add(String.valueOf(this.passenger));
		specList.add(this.color); 
		specList.add(this.ac ?  "yes" :"no");
		specList.add(String.valueOf(this.mpg));
		specList.add(this.sunroof ? "yes" : "no");
		specList.add(this.convertible ? "yes" : "no");
		specList.add(this.condition);		
		specList.add(String.valueOf(this.msrp));
		specList.add(String.valueOf(this.rentalPrice));
		specList.add(this.rented ? "yes" : "no");
		return specList;
	}
	
	/**
	 * a version of toSpecArray that returns an arraylist of objects rather than strings
	 * 0) String make, 1) String country, 2) String vin, 3) String model, 4) int year, 
	 * 5) String size, 6) String type, 7) String plate, 8) String fuel, 9) String trans, 
	 * 10) int door, 11) int passenger, 12) String color, 13) boolean ac, 14) int mpg, 
	 * 15) boolean sunroof, 16) boolean convertible, 17) String condition, 18) int msrp, 
	 * 19) int rentalPrice, 20) boolean rented
	 * 
	 * @deprecated As of version 2.8, deprecated by {@link Car#getFieldMap() getFieldMap()}
	 * @return ArrayList<Object>
	 */
	public ArrayList<Object> toSpecObjectArray() {
		ArrayList<Object> specList = new ArrayList<Object>();
		specList.add(this.make);								// String
		specList.add(this.country);								// String
		specList.add(this.vin);									// String
		specList.add(this.model);								// String
		specList.add(this.year);								// int
		specList.add(this.size);								// String
		specList.add(this.type);								// String
		specList.add(this.plate);								// String
		specList.add(this.fuel);								// String
		specList.add(this.trans);								// String
		specList.add(this.door);								// int
		specList.add(this.passenger);							// int
		specList.add(this.color); 								// String
		specList.add(this.ac);									// boolean
		specList.add(this.mpg);									// int
		specList.add(this.sunroof);								// boolean
		specList.add(this.convertible);							// boolean
		specList.add(this.condition);							// String	
		specList.add(this.msrp);								// int
		specList.add(this.rentalPrice);							// int
		specList.add(this.rented);								// boolean
		return specList;
	}
	
	/**
	 * Builds a map that maps that name of each field
	 * in car to the value of the field.
	 * 
	 * @return A map containing every field in the car object.
	 * @deprecated No longer used as of version 2.9
	 */
	public Map<String, Object> getFieldMap(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("make", this.make);
		map.put("country", this.country);
		map.put("vin", this.vin);
		map.put("model", this.model);
		map.put("year", this.year);
		map.put("size", this.size);
		map.put("type", this.type);
		map.put("plate", this.plate);
		map.put("fuel", this.fuel);
		map.put("trans", this.trans);
		map.put("door", this.door);
		map.put("passenger", this.passenger);
		map.put("color", this.color);
		map.put("ac", this.ac);
		map.put("mpg", this.mpg);
		map.put("sunroof", this.sunroof);
		map.put("convertible", this.convertible);
		map.put("condition", this.condition);
		map.put("msrp", this.msrp);
		map.put("rentalPrice", this.rentalPrice);
		map.put("rented", this.rented);
		map.put("image", this.image);
		return map;
	}
	
	
	/**
	 * Generates a randomly generated VIN number for testing purposes. The number
	 * generated is not guaranteed to be a valid VIN number (in fact, it is highly
	 * unlikely that the generated number will be valid). Despite not being a valid
	 * number, the generated number will be structured like a valid number, in that 
	 * it will have 17 digits and not contain the letters 'I', 'O', or 'Q'.
	 * 
	 * @return Randomly generated VIN number as a string
	 */
	public static String randomVIN(){
		char[] validChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P',    // All characters valid except
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}; // 'I', 'O', and 'Q'
		Random rng = new Random();
		StringBuilder out = new StringBuilder();
		for(int i = 0; i < 17; i++){
			out.append(validChars[rng.nextInt(validChars.length)]); // Digits are randomly appended without regard to standards
		}
		return out.toString();
	}
	
	/**
	 * Creates a randomly generated license plate number for testing purposes. The
	 * number generated is theoretically valid, though it is not checked for duplicates.
	 * The number follows the format for a license plate for a passenger vehicle in 
	 * the City and County of Honolulu.
	 * 
	 * @return Randomly generated license plate number as a String
	 */
	public static String randomLicensePlate(){
		char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		char[] letters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'J', 'N', 'O', 'P', // Honolulu license plate cannot contain 
				'Q','R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'};									// 'H', 'K', 'L', 'M', or 'Z'
		char[] validFirstLetter = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'J', 'N', 'P',  // In addition, first letter cannot be
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'};										// 'I', 'O', or 'Q'
		Random rng = new Random();
		StringBuilder out = new StringBuilder();
		
		out.append(validFirstLetter[rng.nextInt(validFirstLetter.length)]);	// Insert valid first letter
		for(int i = 0; i < 2; i++){
			out.append(letters[rng.nextInt(letters.length)]);				// Insert other letters
		}
		out.append(' ');
		for(int i = 0; i < 3; i++){
			out.append(digits[rng.nextInt(digits.length)]);					// Insert digits
		}
		
		return out.toString();
		
	}
	
	public String getHeadline(){
		return year + " " + make + " " + model;
	}
	
	@Override
	public boolean equals(Object other){
		if(other == null){
			return false;
		}
		if(other instanceof Car){
			return vin.equals(((Car) other).getVIN());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int hash = 0;
		for(int i = 0; i < vin.length(); i++){
			hash += (vin.charAt(i) + 4999) * (i * 19);
		}
		return hash;
	}
	/*public static void main (String[] args) {
		Car myCar = new Car("Honda", "Japan", "23J343K32U8L33223", "NSX", 2006, "sport", "coupe", 
			    			"FAST-N-FURIOUS", "gas", "manual", 2, 2, "black", true, 22, true, false, "new", 89000,50, true);
		
		myCar.printInfo(0);							// vertical print
		System.out.println();						// blank line for readability
		myCar.printInfo(1);							// horizontal print
		System.out.println();						// blank line for readability
		System.out.println(myCar.toSpecArray());	// arraylist print	
		
		
		System.out.println("\n" + myCar.toValues());
		
		ArrayList<Object> carSpec = myCar.toSpecObjectArray();
		System.out.println(carSpec);
		System.out.println();
		String make = (String)carSpec.get(0);	// cast to String
		System.out.println(make);
		
		
		
		
		
	}*/

	public void setMake(String make) {
		this.make = make;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public void setDoor(int door) {
		this.door = door;
	}

	public void setPassenger(int passenger) {
		this.passenger = passenger;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public void setMpg(int mpg) {
		this.mpg = mpg;
	}

	public void setSunroof(boolean sunroof) {
		this.sunroof = sunroof;
	}

	public void setConvertible(boolean convertible) {
		this.convertible = convertible;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void setMsrp(int msrp) {
		this.msrp = msrp;
	}

	public void setRentalPrice(int rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public void setImageBlob(Blob imageBlob) {
		this.imageBlob = imageBlob;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}