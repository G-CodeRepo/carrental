package carrent.util;

public enum Month {

	JANUARY("January", 0, 31),
	FEBRUARY("February", 1, 28),
	MARCH("March", 2, 31),
	APRIL("April", 3, 30),
	MAY("May", 4, 31),
	JUNE("June", 5, 30),
	JULY("July", 6, 31),
	AUGUST("August", 7, 31),
	SEPTEMBER("September", 8, 30),
	OCTOBER("October", 9, 31),
	NOVEMBER("November", 10, 30),
	DECEMBER("December", 11, 31);
	
	private String name;
	private int index;
	private int dayLimit;
	
	private Month(String name, int index, int dayLimit){
		this.name = name;
		this.index = index;
		this.dayLimit = dayLimit;
	}
	
	public int getIndex(){
		return index;
	}
	
	public int getDayLimit(){
		return dayLimit;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
