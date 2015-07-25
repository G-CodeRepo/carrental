package carrent.db;

public interface DatabaseConstants {
	
	public static final String CAR_TABLE = "Cars";
	public static final String ACCOUNT_TABLE = "Account";
	public static final String TRANSACTION_TABLE = "Rental_Transaction";
	public static final String LOCATION_TABLE = "Location";
	public static final String BILLING_ADRESSS_TABLE = "Billing_Address";
	public static final String PAYMENT_OPTION_TABLE = "Payment_Option";
	
	public static final String CAR_MAKE = "make";
	public static final String CAR_COUNTRY = "country";
	public static final String CAR_VIN = "vin";
	public static final String CAR_MODEL = "model";
	public static final String CAR_YEAR = "year";
	public static final String CAR_SIZE = "size";
	public static final String CAR_TYPE = "type";
	public static final String CAR_PLATE = "plate";
	public static final String CAR_FUEL = "fuel";
	public static final String CAR_TRANSMISSION = "transmission";
	public static final String CAR_DOORS = "doors";
	public static final String CAR_PASSENGERS = "passengers";
	public static final String CAR_COLOR = "color";
	public static final String CAR_AC = "ac";
	public static final String CAR_MPG = "mpg";
	public static final String CAR_SUNROOF = "sunroof";
	public static final String CAR_CONVERTIBLE = "convertible";
	public static final String CAR_CONDITION = "condition";
	public static final String CAR_MSRP = "msrp";
	public static final String CAR_RENTALPRICE = "rentalprice";
	public static final String CAR_RENTED = "rented";
	public static final String CAR_LOCATION_ID = "locationID";
	public static final String CAR_IMAGE = "image";
	
	public static final String ACCOUNT_NAME = "acct_name";
	public static final String ACCOUNT_PASSWORD = "password";
	public static final String ACCOUNT_FIRSTNAME = "first_name";
	public static final String ACCOUNT_LASTNAME = "last_name";
	public static final String ACCOUNT_EMPLOYEE = "is_employee";
	public static final String ACCOUNT_EMAIL = "email_address";
	public static final String ACCOUNT_PHONE_NUMBER = "phone_number";
	
	public static final String TRANSACTION_START = "start_date";
	public static final String TRANSACTION_END = "end_date";
	public static final String TRANSACTION_PICKUP = "pickup_date";
	public static final String TRANSACTION_DROPOFF = "dropoff_date";
	public static final String TRANSACTION_VIN = "vin";
	public static final String TRANSACTION_ACCOUNTNAME = "acct_name";
	public static final String TRANSACTION_ID = "tID";
	public static final String TRANSACTION_UPFRONT = "upfront";
	public static final String TRANSACTION_PENALTIES = "penalties";
	public static final String TRANSACTION_PAY_ID = "payID";
	public static final String TRANSACTION_DATE = "transaction_date";
	
	public static final String LOCATION_ID = "locationID";
	public static final String LOCATION_STATE = "state";
	public static final String LOCATION_CITY = "city";
	public static final String LOCATION_ADDRESS = "street_address";
	public static final String LOCATION_ZIP = "zip";
	
	public static final String BILLING_ADDRESS_ACCOUNT_NAME = "acct_name";
	public static final String BILLING_ADDRESS_LOCATION_ID = "locationID";
	public static final String BILLING_ADDRESS_STATE = "state";
	public static final String BILLING_ADDRESS_CITY = "city";
	public static final String BILLING_ADDRESS_STREET = "street_address";
	public static final String BILLING_ADDRESS_ZIP = "zip";
	public static final String BILLING_ADDRESS_SELECTED = "selected";
	
	public static final String PAYMENT_OPTION_PAYTYPE = "paytype";
	public static final String PAYMENT_OPTION_ID = "payID";
	public static final String PAYMENT_OPTION_ACCOUNT_NAME = "acct_name";
	public static final String PAYMENT_OPTION_CARD_HOLDER = "card_holder";
	public static final String PAYMENT_OPTION_CARD_NUMBER = "card_number";
	public static final String PAYMENT_OPTION_CARD_COMPANY = "card_company";
	public static final String PAYMENT_OPTION_CSV = "csv";
	public static final String PAYMENT_OPTION_EXPIRATION_MONTH = "expiration_month";
	public static final String PAYMENT_OPTION_EXPIRATION_YEAR = "expiration_year";
	public static final String PAYMENT_OPTION_SELECTED = "selected";
	
	public static final int CAR_MAKE_LIMIT = 50;
	public static final int CAR_COUNTRY_LIMIT = 100;
	public static final int CAR_VIN_LIMIT = 17;
	public static final int CAR_MODEL_LIMIT = 50;
	public static final int CAR_SIZE_LIMIT = 20;
	public static final int CAR_TYPE_LIMIT = 20;
	public static final int CAR_PLATE_LIMIT = 7;
	public static final int CAR_FUEL_LIMIT = 10;
	public static final int CAR_TRANSMISSION_LIMIT = 10;
	public static final int CAR_COLOR_LIMIT = 50;
	public static final int CAR_CONDITION_LIMIT = 256;
	
	public static final int ACCOUNT_NAME_LIMIT = 30;
	public static final int ACCOUNT_PASSWORD_LIMIT = 30;
	public static final int ACCOUNT_FIRSTNAME_LIMIT = 50;
	public static final int ACCOUNT_LASTNAME_LIMIT = 50;
	public static final int ACCOUNT_EMAIL_LIMIT = 100;
	public static final int ACCOUNT_PHONE_NUMBER_LIMIT = 50;
	
	public static final int TRANSACTION_VIN_LIMIT = 17;
	public static final int TRANSACTION_ACCOUNTNAME_LIMIT = 30;
	public static final int TRANSACTION_PAYTYPE_LIMIT = 20;
	
	public static final int LOCATION_STATE_LIMIT = 50;
	public static final int LOCATION_CITY_LIMIT = 100;
	public static final int LOCATION_ADDRESS_LIMIT = 100;
	
	public static final int BILLING_ADDRESS_ACCOUNT_NAME_LIMIT = 30;
	public static final int BILLING_ADDRESS_STATE_LIMIT = 50;
	public static final int BILLING_ADDRESS_CITY_LIMIT = 100;
	public static final int BILLING_ADDRESS_STREET_LIMIT = 100;
	
	public static final int PAYMENT_OPTION_PAYTYPE_LIMIT = 20;
	public static final int PAYMENT_OPTION_ACCOUNT_NAME_LIMIT = 30;
	public static final int PAYMENT_OPTION_CARD_HOLDER_LIMIT = 100;
	public static final int PAYMENT_OPTION_CARD_NUMBER_LIMIT = 20;
	public static final int PAYMENT_OPTION_CARD_COMPANY_LIMIT = 100;
	
	public static final String QUERY_ERROR = "bad query";
}
