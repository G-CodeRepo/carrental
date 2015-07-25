package carrent.entity;

public class PaymentOption {
	
	private String paytype;
	private int payID;
	private String accountName;
	private String cardHolder;
	private String cardNumber;
	private String cardCompany;
	private int csv;
	private int expirationMonth;
	private int expirationYear;
	private boolean selected;
	
	public PaymentOption(String paytype, int payID, String accountName, String cardHolder, String cardNumber,
			             String cardCompany, int csv, int expirationMonth, int expirationYear, boolean selected) {
		this.paytype = paytype;
		this.payID = payID;
		this.accountName = accountName;
		this.cardHolder = cardHolder;
		this.cardNumber = cardNumber;
		this.cardCompany = cardCompany;
		this.csv = csv;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public int getPayID() {
		return payID;
	}
	public void setPayID(int payID) {
		this.payID = payID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardCompany() {
		return cardCompany;
	}
	public void setCardCompany(String cardCompany) {
		this.cardCompany = cardCompany;
	}
	public int getCsv() {
		return csv;
	}
	public void setCsv(int csv) {
		this.csv = csv;
	}
	public int getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public int getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
}
