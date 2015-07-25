package carrent.util;

import java.util.Comparator;

import carrent.entity.Car;

public class CarComparator implements Comparator<Car> {
	
	public static final int MAKE_A = -1;
	public static final int MAKE_D = 1;
	public static final int MODEL_A = -2;
	public static final int MODEL_D = 2;
	public static final int YEAR_A = 3;
	public static final int YEAR_D = -3;
	public static final int DOOR_A = 4;
	public static final int DOOR_D = -4;
	public static final int PASSENGER_A = 5;
	public static final int PASSENGER_D = -5;
	public static final int TRANSMISSION_A = 6;
	public static final int TRANSMISSION_D = -6;
	public static final int FUEL_A = -7;
	public static final int FUEL_D = 7;
	public static final int PRICE_A = 8;
	public static final int PRICE_D = -8;
	
	
	private int mode;
	
	public CarComparator(){
		this.mode = MAKE_A;
	}
	
	public CarComparator(int mode){
		this.mode = mode;
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	@Override
	public int compare(Car c1, Car c2) {
		switch(mode){
			case MAKE_A : return c1.getMake().compareTo(c2.getMake());
			case MAKE_D : return c2.getMake().compareTo(c1.getMake());
			case MODEL_A : return c1.getModel().compareTo(c2.getModel());
			case MODEL_D : return c2.getModel().compareTo(c1.getModel());
			case YEAR_A : return c1.getYear() - c2.getYear();
			case YEAR_D : return c2.getYear() - c1.getYear();
			case DOOR_A : return c1.getDoor() - c2.getDoor();
			case DOOR_D : return c2.getDoor() - c1.getDoor();
			case PASSENGER_A : return c1.getPassenger() - c2.getPassenger();
			case PASSENGER_D : return c2.getPassenger() - c1.getPassenger();
			case TRANSMISSION_A : return c1.getTransmission().compareTo(c2.getTransmission());
			case TRANSMISSION_D : return c2.getTransmission().compareTo(c1.getTransmission());
			case FUEL_A : return c1.getFuel().compareTo(c2.getFuel());
			case FUEL_D : return c2.getFuel().compareTo(c1.getFuel());
			case PRICE_A : return c1.getRentalPrice() - c2.getRentalPrice();
			case PRICE_D : return c2.getRentalPrice() - c1.getRentalPrice();
			default : throw new IllegalArgumentException();
		}
	}

}
