package carrent.entity;

public class BillingAddress {
	
	private String accountName;
	private String name;
	private int locationID;
	private String state;
	private String city;
	private String street;
	private int zip;
	private boolean selected;
	
	public BillingAddress(String accountName, String name, int locationID, String state, String city,
			               String street, int zip, boolean selected) {
		this.accountName = accountName;
		this.name = name;
		this.locationID = locationID;
		this.state = state;
		this.city = city;
		this.street = street;
		this.zip = zip;
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
