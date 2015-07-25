/**
 * DBTester class uses the DBInterface methods to perform tests
 * @author Team G.A.S
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sql.rowset.serial.SerialBlob;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Car;
import carrent.util.SearchOperator;

public class DBTester implements DatabaseConstants {
	DBInterface db;
	
	/**
	 * DBTester works on the given DBInterface to perform DB tests
	 * @param database
	 */
	public DBTester (DBInterface database) {
		this.db = database;
	}
	
	public DBTester () throws ClassNotFoundException {
		DBInterface.loadDB();
	}
	
	/**
	 * runTest runs different DB tests
	 */
	public void runTest() {
		Iterator<Car> cr;
		QueryBuilder qb;
		ArrayList<Car> results;
		boolean test = false;
		int count = 0;
		int dbTotal = 0;
		Long time;
		String make;	
		String country;
		String vin;					
		String model;
		int year;
		String size;
		String bodyType;
		String plate;
		String fuel;
		String trans;
		int door;
		int passenger;
		String color;
		boolean ac;
		int mpg;
		boolean sunroof;
		boolean convertible;
		String condition;
		int msrp;
		int rentalPrice;
		boolean rented;
		int locationID;
		
		// count tuples in table cars
		qb = new QueryBuilder("*", CAR_TABLE);
	
		results = DBInterface.queryCars(qb.toString());
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s\n", c.getMake(), c.getModel());
			dbTotal++;
		}
		
		// range query (intentional fail, zero return)
		time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		door = 3;
		qb.addString(CAR_MAKE, "Honda");	// the hondas in the database don't have 3 doors
		qb.addInt(CAR_DOORS, door, SearchOperator.EQUAL);
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s%-15d\n", c.getMake(), c.getModel(), c.getDoor());
			if (c.getDoor() > door || c.getDoor() < door) {
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;
		
		// range query (1 possible)
		time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		rentalPrice = 2000;
		qb.addInt(CAR_RENTALPRICE, rentalPrice, SearchOperator.GREATER_EQUAL);	// only one car as a rental price at least $2000
		results =DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-15d\n", c.getMake(), c.getRentalPrice());
			if (c.getRentalPrice() < rentalPrice) {
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;
		
		// range query (1/2 of the database possible)
		time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		door = 4;
		qb.addInt(CAR_DOORS, door, SearchOperator.GREATER_EQUAL);	// approx 50% of the cars have at least 4 doors
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-25s%-2d\n", c.getMake(), c.getModel(), c.getDoor());
			if (c.getDoor() < door) {
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;
		
		// range query (all)
		time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		if (results.size() > 0) {
			test = true;
		}
		/*cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			System.out.printf("%-20s%-20s\n", c.getMake(), c.getModel());
			count++;
		}
		System.out.println("count: " + count);
		*/
		if (count == dbTotal) {
			test = true;
		}
		this.printResults(qb, time, test);
		test = false;
		count = 0;
		
		// point query (intentional fail, zero return)
		time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		color = "Yellow";		// the NSX is black
		qb.addString(CAR_MAKE, "Honda");
		qb.addString(CAR_MODEL, "NSX");
		qb.addString(CAR_COLOR, color);
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s%-20s\n", c.getMake(), c.getModel(), c.getColor());
			if (!c.getColor().equals(color)) {
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;
		
		// point query (1 possible)
		time = System.currentTimeMillis();
		model = "Granturismo Sport";
		year = 2013;
		qb = new QueryBuilder("*", CAR_TABLE);
		qb.addString(CAR_MODEL, model);
		qb.addInt(CAR_YEAR,2013, SearchOperator.EQUAL);
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s%-5d\n", c.getMake(), c.getModel(), c.getYear());
			if (!c.getModel().equals(model)) {
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;
				
		// point query (double check for previous test)
		/*time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		color = "Black";
		qb.addString(CAR_MAKE, "Honda");
		qb.addString(CAR_MODEL, "NSX");
		qb.addString(CAR_COLOR, color);
		results = db.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s%-20s\n", c.getMake(), c.getModel(), c.getColor());
			if (!c.getColor().equals(color)) {
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;*/
		
		// point query (1/2 of the database possible)
		time = System.currentTimeMillis();
		bodyType = "Coupe";
		qb = new QueryBuilder("*", CAR_TABLE);	//
		qb.addString(CAR_TYPE, bodyType);
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s%-15s\n", c.getMake(), c.getModel(), c.getBodyType());
			if (!c.getBodyType().equals(bodyType)){
				test = false;
				break;
			} else {
				test = true;
			}
		}
		this.printResults(qb, time, test);
		test = false;
		
		// point query (all)
		time = System.currentTimeMillis();
		qb = new QueryBuilder("*", CAR_TABLE);
		qb.addBool(CAR_AC, false);	// for this current database, all cars have ac
		results = DBInterface.queryCars(qb.toString());
		time = System.currentTimeMillis() - time;
		cr = results.iterator();
		while (cr.hasNext()) {
			Car c = cr.next();
			//System.out.printf("%-20s%-20s%-20s\n", c.getMake(), c.getModel(), c.hasAC());
			count++;
		}
		//System.out.println(count);
		if (count == dbTotal) {
			test = true;
		}
		this.printResults(qb, time, test);
		test = false;
		count = 0;
				
		// deletion
		qb = new QueryBuilder("*", CAR_TABLE);
		model = "IQ";
		qb.addString(CAR_MODEL, model);
		results = DBInterface.queryCars(qb.toString());
		
		Car rem = results.get(0);
		
		// used to reinsert back into the database 
		make = rem.getMake();	
		country = rem.getCountry();
		vin = rem.getVIN();						
		model = rem.getModel();	
		year = rem.getYear();
		size = rem.getSize();
		bodyType = rem.getBodyType();
		plate = rem.getPlate();
		fuel = rem.getFuel();
		trans = rem.getTransmission();
		door = rem.getDoor();
		passenger = rem.getPassenger();
		color = rem.getColor();
		ac = rem.hasAC();
		mpg = rem.getMPG();
		sunroof = rem.isSunroof();
		convertible = rem.isConvertible();
		condition = rem.getCondition();
		msrp = rem.getMSRP();
		rentalPrice = rem.getRentalPrice();
		rented = rem.isRented();
		locationID = rem.getLocationID();
		
		time = System.currentTimeMillis();
		int del = DBInterface.deleteCar(rem.getVIN());
		time = System.currentTimeMillis() - time;
		if (del == 0) {
			test = true;
			//System.out.println("Successfully deleted: " + make + " " + model + ", vin: " + vin);
			this.printResults(qb, time, test);
		} else {
			test = false;
			this.printResults(qb, time, test);
		}	
		test = false;
		
		// insertion (Anthony's Sarria's Code for Blob)
		byte[] bytes = new byte[0];
		try{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("carpix/" + make + "/" + model + ".png"));
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
			System.out.println("MALFORMED BLOB");
		}
		
		Car newCar = new Car(make, country, vin, model, year, size, bodyType, plate, fuel, trans, door, passenger, color, ac, mpg, sunroof, convertible,
							 condition, msrp, rentalPrice, rented, locationID, imageBlob);
		time = System.currentTimeMillis();
		int add = DBInterface.addCar(newCar);
		time = System.currentTimeMillis() - time;
		if (add == 0) {
			//System.out.println("Successfully added: " + make + " " + model + ", vin: " + vin);
			test = true;
			this.printResults(qb, time, test);
		} else {
			test = false;
			this.printResults(qb, time, test);
		}
	}
	
	/**
	 * printResults prints out the test results
	 * displaying query gets long sometimes so the cell was extended
	 * @param qb
	 * @param time
	 * @param test
	 */
	private void printResults(QueryBuilder qb, Long time, boolean test) {
		if (test) {
			System.out.printf("%-7s%-80s%-10s%-11s%-8s\n", "Query: ", qb.toString(), "Time (ms): ", String.valueOf(time), "[PASS]");
		} else {
			System.out.printf("%-7s%-80s%-10s%-11s%-8s\n", "Query: ", qb.toString(), "Time (ms): ", String.valueOf(time), "[FAIL]");
		}
	}
		
	public static void main (String[] args) {
			DBTester t;
			try {
				t = new DBTester();
				t.runTest();
			} catch (ClassNotFoundException e) {
				System.out.println("CLASS NOT FOUND");
			}
			DBInterface.closeConnection();
	}	
}