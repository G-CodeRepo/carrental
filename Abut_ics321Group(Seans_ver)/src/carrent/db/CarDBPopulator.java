package carrent.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import carrent.entity.Car;

public class CarDBPopulator {

	public static void populateDB(boolean verbose) throws SerialException, SQLException, NumberFormatException, IOException, ClassNotFoundException{
		DBInterface.loadDB();
		
		BufferedReader reader = new BufferedReader(new FileReader("vehicleslist.csv"));
		
		String next;
		ArrayList<Car> carList = new ArrayList<>();
		
		while((next = reader.readLine()) != null){
			String[] data = next.split(",");
	//		System.out.println(Arrays.toString(data));
			String make = data[0];
			String country = data[1];
			String vin = Car.randomVIN();
			String model = data[3];
			int year = Integer.parseInt(data[4]);
			String size = data[5];
			String type = data[6];
			String plate = Car.randomLicensePlate();
			String fuel = data[8];
			String trans = data[9];
			int door = Integer.parseInt(data[10]);
			int passenger = Integer.parseInt(data[11]);
			String color = data[12];
			boolean ac = data[13].equalsIgnoreCase("yes");
			int mpg = 0;
			if(!data[14].equals("n/a")){
				mpg = Integer.parseInt(data[14]);
			}
			boolean sunroof = data[15].equalsIgnoreCase("yes");
			boolean convertible = data[16].equalsIgnoreCase("yes");
			String condition = "new";
			int msrp = Integer.parseInt(data[17]);
			int rentalPrice = (msrp / 10000) * 10;
			rentalPrice = rentalPrice == 0 ? 10 : rentalPrice;
			int locationID = Integer.parseInt(data[19]);
			boolean rented = false;
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
			
			
			SerialBlob imageBlob = new SerialBlob(bytes);
			if(verbose){
				System.out.println(make + "," + country + "," + vin + "," + model + "," + year + "," + 
								   size + "," + type + "," + plate + "," + fuel + "," + trans + "," +
								   door + "," + passenger + "," + color + "," + (ac?1:0) + "," + mpg +
								   "," + (sunroof?1:0) + "," + (convertible?1:0) + "," + condition + "," +
								   msrp + "," + rentalPrice + "," + (rented?1:0) + "," + locationID);
			}
			carList.add(new Car(make, country, vin, model, year, size, type, plate, 
					fuel, trans, door, passenger, color, ac, mpg, sunroof, convertible,
					condition, msrp, rentalPrice, rented, locationID, imageBlob));
		}
		reader.close();
		for(int i = 0; i < carList.size(); i++){
			DBInterface.addCar(carList.get(i));
		}
		
		DBInterface.closeConnection();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, SerialException, SQLException{
		
		populateDB(true);
		
	}
}
