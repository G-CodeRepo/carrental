package carrent.entity;

public class Location {
	private int locationID;
	private String state;
	private String city;
	private String street;
	private int zip;
	
	public Location(int locationID, String state, String city, String street, int zip) {
		this.locationID = locationID;
		this.state = state;
		this.city = city;
		this.street = street;
		this.zip = zip;
	}
	
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
}
