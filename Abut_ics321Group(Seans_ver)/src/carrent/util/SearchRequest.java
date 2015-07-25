package carrent.util;

import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;

public class SearchRequest implements DatabaseConstants{

	private String make;
	private String country;
	private String vin;
	private String model;
	private Integer year;
	private SearchOperator yearOp;
	private String size;
	private String type;
	private String plate;
	private String fuel;
	private String trans;
	private Integer door;
	private SearchOperator doorOp;
	private Integer passenger;
	private SearchOperator passengerOp;
	private String color; 
	private Boolean ac;
	private Integer mpg;
	private SearchOperator mpgOp;
	private Boolean sunroof;
	private Boolean convertible;
	private String condition;
	private Integer msrp;
	private SearchOperator msrpOp;
	private Integer rentalPrice;
	private SearchOperator priceOp;
	private Boolean rented;
	
	public SearchRequest(){
		this.make = null;
		this.country = null;
		this.vin = null;
		this.model = null;
		this.year = null;
		this.size = null;
		this.type = null;
		this.plate = null;
		this.fuel = null;
		this.trans = null;
		this.door = null;
		this.passenger = null;
		this.color = null; 
		this.ac = null;
		this.mpg = null;
		this.sunroof = null;
		this.convertible = null;
		this.condition = null;
		this.msrp = null;
		this.rentalPrice = null;
		this.rented = null;
		
		this.setYearOp(SearchOperator.EQUAL);
		this.doorOp = SearchOperator.EQUAL;
		this.passengerOp = SearchOperator.EQUAL;
		this.mpgOp = SearchOperator.EQUAL;
		this.msrpOp = SearchOperator.EQUAL;
		this.priceOp = SearchOperator.EQUAL;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the yearOp
	 */
	public SearchOperator getYearOp() {
		return yearOp;
	}

	/**
	 * @param yearOp the yearOp to set
	 */
	public void setYearOp(SearchOperator yearOp) {
		this.yearOp = yearOp;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the plate
	 */
	public String getPlate() {
		return plate;
	}

	/**
	 * @param plate the plate to set
	 */
	public void setPlate(String plate) {
		this.plate = plate;
	}

	/**
	 * @return the fuel
	 */
	public String getFuel() {
		return fuel;
	}

	/**
	 * @param fuel the fuel to set
	 */
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return the trans
	 */
	public String getTrans() {
		return trans;
	}

	/**
	 * @param trans the trans to set
	 */
	public void setTrans(String trans) {
		this.trans = trans;
	}

	/**
	 * @return the door
	 */
	public Integer getDoor() {
		return door;
	}

	/**
	 * @param door the door to set
	 */
	public void setDoor(Integer door) {
		this.door = door;
	}

	/**
	 * @return the doorOp
	 */
	public SearchOperator getDoorOp() {
		return doorOp;
	}

	/**
	 * @param doorOp the doorOp to set
	 */
	public void setDoorOp(SearchOperator doorOp) {
		this.doorOp = doorOp;
	}

	/**
	 * @return the passenger
	 */
	public Integer getPassenger() {
		return passenger;
	}

	/**
	 * @param passenger the passenger to set
	 */
	public void setPassenger(Integer passenger) {
		this.passenger = passenger;
	}

	/**
	 * @return the passengerOp
	 */
	public SearchOperator getPassengerOp() {
		return passengerOp;
	}

	/**
	 * @param passengerOp the passengerOp to set
	 */
	public void setPassengerOp(SearchOperator passengerOp) {
		this.passengerOp = passengerOp;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the ac
	 */
	public Boolean getAc() {
		return ac;
	}

	/**
	 * @param ac the ac to set
	 */
	public void setAc(Boolean ac) {
		this.ac = ac;
	}

	/**
	 * @return the mpg
	 */
	public Integer getMpg() {
		return mpg;
	}

	/**
	 * @param mpg the mpg to set
	 */
	public void setMpg(Integer mpg) {
		this.mpg = mpg;
	}

	/**
	 * @return the mpgOp
	 */
	public SearchOperator getMpgOp() {
		return mpgOp;
	}

	/**
	 * @param mpgOp the mpgOp to set
	 */
	public void setMpgOp(SearchOperator mpgOp) {
		this.mpgOp = mpgOp;
	}

	/**
	 * @return the sunroof
	 */
	public Boolean getSunroof() {
		return sunroof;
	}

	/**
	 * @param sunroof the sunroof to set
	 */
	public void setSunroof(Boolean sunroof) {
		this.sunroof = sunroof;
	}

	/**
	 * @return the convertible
	 */
	public Boolean getConvertible() {
		return convertible;
	}

	/**
	 * @param convertible the convertible to set
	 */
	public void setConvertible(Boolean convertible) {
		this.convertible = convertible;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the msrp
	 */
	public Integer getMsrp() {
		return msrp;
	}

	/**
	 * @param msrp the msrp to set
	 */
	public void setMsrp(Integer msrp) {
		this.msrp = msrp;
	}

	/**
	 * @return the msrpOp
	 */
	public SearchOperator getMsrpOp() {
		return msrpOp;
	}

	/**
	 * @param msrpOp the msrpOp to set
	 */
	public void setMsrpOp(SearchOperator msrpOp) {
		this.msrpOp = msrpOp;
	}

	/**
	 * @return the rentalPrice
	 */
	public Integer getRentalPrice() {
		return rentalPrice;
	}

	/**
	 * @param rentalPrice the rentalPrice to set
	 */
	public void setRentalPrice(Integer rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	/**
	 * @return the priceOp
	 */
	public SearchOperator getPriceOp() {
		return priceOp;
	}

	/**
	 * @param priceOp the priceOp to set
	 */
	public void setPriceOp(SearchOperator priceOp) {
		this.priceOp = priceOp;
	}

	/**
	 * @return the rented
	 */
	public Boolean getRented() {
		return rented;
	}

	/**
	 * @param rented the rented to set
	 */
	public void setRented(Boolean rented) {
		this.rented = rented;
	}
	
	public QueryBuilder generateSearchQuery(){
		QueryBuilder query = new QueryBuilder("*", CAR_TABLE);
		
		if(make != null){
			query.addString(CAR_MAKE, make);
		}
		if(country != null){
			query.addString(CAR_COUNTRY, country);
		}
		if(vin != null){
			query.addString(CAR_VIN, vin);
		}
		if(model != null){
			query.addString(CAR_MODEL, model);
		}
		if(year != null && yearOp != null){
			query.addInt(CAR_YEAR, year, yearOp);
		}
		if(size != null){
			query.addString(CAR_SIZE, size);
		}
		if(type != null){
			query.addString(CAR_TYPE, type);
		}
		if(plate != null){
			query.addString(CAR_PLATE, plate);
		}
		if(fuel != null){
			query.addString(CAR_FUEL, fuel);
		}
		if(trans != null){
			query.addString(CAR_TRANSMISSION, trans);
		}
		if(door != null && doorOp != null){
			query.addInt(CAR_DOORS, door, doorOp);
		}
		if(passenger != null && passengerOp != null){
			query.addInt(CAR_PASSENGERS, passenger, passengerOp);
		}
		if(color != null){
			query.addString(CAR_COLOR, color);
		}
		if(ac != null){
			query.addBool(CAR_AC, ac);
		}
		if(mpg != null && mpgOp != null){
			query.addInt(CAR_MPG, mpg, mpgOp);
		}
		if(sunroof != null){
			query.addBool(CAR_SUNROOF, sunroof);
		}
		if(convertible != null){
			query.addBool(CAR_CONVERTIBLE, convertible);
		}
		if(condition != null){
			query.addString(CAR_CONDITION, condition);
		}
		if(msrp != null && msrpOp != null){
			query.addInt(CAR_MSRP, msrp, msrpOp);
		}
		if(rentalPrice != null && priceOp != null){
			query.addInt(CAR_RENTALPRICE, rentalPrice, priceOp);
		}
		if(rented != null){
			query.addBool(CAR_RENTED, rented);
		}
		
		return query;
		
	}
}
