package carrent.util;

public enum ComparatorOptionA {
	
	MAKE("Make", 1),
	MODEL("Model", 2),
	YEAR("Year", 3),
	DOOR("Doors", 4),
	PASSENGER("Passengers", 5),
	TRANSMISSION("Transmission", 6),
	FUEL("Fuel Type", 7),
	PRICE("Price", 8);	
	
	private String name;
	private int value;
	
	private ComparatorOptionA(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public int getValue(){
		return value;
	}
	
}
