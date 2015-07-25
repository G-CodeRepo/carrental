package carrent.entity;

import java.sql.*;

public class Transaction {
	private Timestamp startDate; 
	private Timestamp endDate;
	private Timestamp pickupDate;
	private Timestamp dropoffDate;
	private String vin; 
	private String acctName;
	private int tID;
	private int upfront;
	private int penalties;
	private int payID;
	private Timestamp transactionDate; 
	
	public Transaction(Timestamp startDate, Timestamp endDate, Timestamp pickupDate,
			Timestamp dropoffDate, String vin, String acctName, int tID, int upfront,
			int penalties, int payID, Timestamp transactionDate ) {
		this.startDate = startDate; 
		this.endDate = endDate;
		this.pickupDate = pickupDate;
		this.dropoffDate = dropoffDate;
		this.vin = vin; 
		this.acctName = acctName;
		this.tID = tID;
		this.upfront = upfront;
		this.penalties = penalties;
		this.payID = payID;
		this.transactionDate = transactionDate;
		
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Timestamp pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Timestamp getDropoffDate() {
		return dropoffDate;
	}

	public void setDropoffDate(Timestamp dropoffDate) {
		this.dropoffDate = dropoffDate;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public int gettID() {
		return tID;
	}

	public void settID(int tID) {
		this.tID = tID;
	}

	public int getUpfront() {
		return upfront;
	}

	public void setUpfront(int upfront) {
		this.upfront = upfront;
	}

	public int getPenalties() {
		return penalties;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public int getPayID() {
		return payID;
	}

	public void setPayID(int payID) {
		this.payID = payID;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

}