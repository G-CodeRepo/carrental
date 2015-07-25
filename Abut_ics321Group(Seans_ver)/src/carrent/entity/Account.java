package carrent.entity;

import carrent.db.DatabaseConstants;

public class Account implements DatabaseConstants{
	
	private String accountName;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email; 
	private boolean isEmployee;
	
	public Account(String accountName, String password, String firstName, String lastName, boolean isEmployee)
	{ 
		this.accountName = accountName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = "None";
		this.email = "None";
		this.isEmployee = isEmployee;
	}
	
	public Account(String accountName, String password, String firstName, String lastName, String phoneNumber, String email, boolean isEmployee)
	{ 
		this.accountName = accountName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isEmployee = isEmployee;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	public Object[] toTableData(){
		return new Object[]{accountName, password, firstName, lastName, phoneNumber, email, (isEmployee ? "Yes" : "No")};
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Account){
			return accountName.equals(((Account) o).getAccountName());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int hash = 0;
		for(int i = 0; i < accountName.length(); i++){
			hash += (accountName.charAt(i) + 5003) * (i * 23);
		}
		return hash;
	}
	
	@Override
	public String toString(){
	//	return String.format("%" + ACCOUNT_NAME_LIMIT + "s - %" + ACCOUNT_PASSWORD_LIMIT + "s - %" + 
	//						 ACCOUNT_FIRSTNAME_LIMIT + "s - %" + ACCOUNT_LASTNAME_LIMIT + "s - %1s", 
	//						 accountName, password, firstName, lastName, isEmployee ? "Y":"N");
		return (isEmployee ? "[A] " : "      ") + accountName + ": " + firstName + " " + lastName;
	}
	
	
}