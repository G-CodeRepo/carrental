package carrent.db;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.String;


import carrent.entity.Account;
import carrent.entity.Car;
import carrent.entity.Location;
import carrent.entity.PaymentOption;
import carrent.entity.Transaction;

/* Change log:
 * 	
 *		Version 1.1: - Method checkPassword() now returns the verified Account object if verification
 *					   is successful, null if it fails.
 *					 - ClassNotFoundException is no longer caught here so proper action can be taken 
 *					   by invokers of connection methods.
 *
 *		Version 1.2: - Changed all references to tables and columns in tables to constants.
 *					 - Improved error logging functionality.
 * 
 */

/**
 * 
 * 
 * @version 1.2
 */
public class DBInterface implements DatabaseConstants{

	private static Connection connection;

	private static Properties db2ConnProps = new Properties();

	public static void loadDB() throws ClassNotFoundException {
		String db, userName, passwd, host, port;
		
		@SuppressWarnings("unused")
		String interactive = "";
		
		@SuppressWarnings("unused")
		String rollbackcommit = "rollback";
		
		try {
			db2ConnProps.load(new FileInputStream("carrentaldb.properties"));
			db = db2ConnProps.getProperty("databaseName");
			userName = db2ConnProps.getProperty("userName");
			passwd = db2ConnProps.getProperty("password");
			host = db2ConnProps.getProperty("hostName");
			port = db2ConnProps.getProperty("portNumber");
			rollbackcommit = db2ConnProps.getProperty("rollbackCommit");
			interactive = db2ConnProps.getProperty("interactive");

			connection = openConnection(db, userName, passwd, host, port);

		} catch (NullPointerException e) {
			System.err.println("CRITICAL ERROR - Missing or malformed entry in database properties file");
		} catch (IOException io) {
			System.err.println("CRIITCAL ERROR - Cannot load from database properties file");
		}
	}

	private static Connection openConnection(String db, String userName,
			String passwd, String host, String port) throws ClassNotFoundException{
		Connection conn = null;

		try {
			
			/** Load the DB2(R) JCC driver with DriverManager **/
			Class.forName("com.ibm.db2.jcc.DB2Driver");

			/** Create Database URL and establish DB Connection **/
			String databaseURL = "jdbc:db2://" + host + ":" + port + "/" + db;
			java.util.Properties properties = new java.util.Properties();
			properties.setProperty("user", userName);
			properties.setProperty("password", passwd);
			conn = DriverManager.getConnection(databaseURL, properties);

			/** print any error messages **/
			if (conn == null)
				System.err.println("ERROR - Database connection failed");
		}
		/*
		catch(DisconnectNonTransientConnectionException e){
			System.err.println("CRITICAL ERROR - Cannot find database server");
			System.exit(3);
		}
		catch(SqlNonTransientConnectionException e){
			System.err.println("CRITICAL ERROR - Bad database hostname");
			System.exit(1);
		}
		catch(SqlSyntaxErrorException e){
			System.err.println("CRITICAL ERROR - Malformed database URL");
			System.exit(2);
		}*/
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return conn;

	}

	public static void closeConnection() {
		try {
			connection.close();

		} catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
			System.exit(1);
		}
	}
	
	public static ArrayList<Car> queryCars(String query){
		ArrayList<Car> resultCars = new ArrayList<Car>();
		Statement selectStmt;
		ResultSet resultSet;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(query);

			while (resultSet.next()) {
				resultCars.add(new Car(resultSet.getString(CAR_MAKE),
						               resultSet.getString(CAR_COUNTRY),
						               resultSet.getString(CAR_VIN),
						               resultSet.getString(CAR_MODEL),
						               resultSet.getInt(CAR_YEAR),
						               resultSet.getString(CAR_SIZE),
						               resultSet.getString(CAR_TYPE),
						               resultSet.getString(CAR_PLATE),
						               resultSet.getString(CAR_FUEL), 
						               resultSet.getString(CAR_TRANSMISSION),
						               resultSet.getInt(CAR_DOORS),
						               resultSet.getInt(CAR_PASSENGERS),
						               resultSet.getString(CAR_COLOR),
						               resultSet.getBoolean(CAR_AC),
						               resultSet.getInt(CAR_MPG),
						               resultSet.getBoolean(CAR_SUNROOF),
						               resultSet.getBoolean(CAR_CONVERTIBLE),
						               resultSet.getString(CAR_CONDITION), 
						               resultSet.getInt(CAR_MSRP),
						               resultSet.getInt(CAR_RENTALPRICE),
						               resultSet.getBoolean(CAR_RENTED),
						               resultSet.getInt(CAR_LOCATION_ID),
						               resultSet.getBlob(CAR_IMAGE)));
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return resultCars;

	}
	
	public static ArrayList<Account> queryAccounts(String query) {
		ArrayList<Account> resultTransactions = new ArrayList<Account>();
		Statement selectStmt;
		ResultSet resultSet;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(query);

			while (resultSet.next()) {
				resultTransactions.add(new Account(resultSet.getString(ACCOUNT_NAME),
												   resultSet.getString(ACCOUNT_PASSWORD),
												   resultSet.getString(ACCOUNT_FIRSTNAME),
												   resultSet.getString(ACCOUNT_LASTNAME),
												   resultSet.getBoolean(ACCOUNT_EMPLOYEE)));
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return resultTransactions;

	}
	
	public static ArrayList<Transaction> queryTransactions(String query) {
		ArrayList<Transaction> resultTransactions = new ArrayList<Transaction>();
		Statement selectStmt;
		ResultSet resultSet;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(query);

			while (resultSet.next()) {
				resultTransactions.add(new Transaction(resultSet.getTimestamp(TRANSACTION_START), 
						                               resultSet.getTimestamp(TRANSACTION_END),
						                               resultSet.getTimestamp(TRANSACTION_PICKUP),
						                               resultSet.getTimestamp(TRANSACTION_DROPOFF),
						                               resultSet.getString(TRANSACTION_VIN),
						                               resultSet.getString(TRANSACTION_ACCOUNTNAME),
						                               resultSet.getInt(TRANSACTION_ID),
						                               resultSet.getInt(TRANSACTION_UPFRONT),
						                               resultSet.getInt(TRANSACTION_PENALTIES),
						                               resultSet.getInt(TRANSACTION_PAY_ID),
						                               resultSet.getTimestamp(TRANSACTION_DATE)));
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return resultTransactions;

	}
	
	public static ArrayList<PaymentOption> queryPaymentOptions(String query) {
		ArrayList<PaymentOption> resultOptions = new ArrayList<PaymentOption>();
		Statement selectStmt;
		ResultSet resultSet;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(query);

			while (resultSet.next()) {
				resultOptions.add(new PaymentOption(resultSet.getString(PAYMENT_OPTION_PAYTYPE),
						                            resultSet.getInt(PAYMENT_OPTION_ID),
						                            resultSet.getString(PAYMENT_OPTION_ACCOUNT_NAME),
						                            resultSet.getString(PAYMENT_OPTION_CARD_HOLDER),
						                            resultSet.getString(PAYMENT_OPTION_CARD_NUMBER),
						                            resultSet.getString(PAYMENT_OPTION_CARD_COMPANY),
						                            resultSet.getInt(PAYMENT_OPTION_CSV),
						                            resultSet.getInt(PAYMENT_OPTION_EXPIRATION_MONTH),
						                            resultSet.getInt(PAYMENT_OPTION_EXPIRATION_YEAR),
						                            resultSet.getBoolean(PAYMENT_OPTION_SELECTED)));
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return resultOptions;

	}
	
	public static int getNextTID(){
		if(queryTransactions("SELECT * FROM " + TRANSACTION_TABLE).size() == 0){
			return 0;
		}
		else{
			return getMax(TRANSACTION_ID, TRANSACTION_TABLE) + 1;
		}
	}
	
	public static int getNextPID(){
		if(queryPaymentOptions("SELECT * FROM " + PAYMENT_OPTION_TABLE).size() == 0){
			return 0;
		}
		else{
			return getMax(PAYMENT_OPTION_ID, PAYMENT_OPTION_TABLE) + 1;
		}
	}
	
	
	public static int addCar(Car c) {
		Statement checkStmt;
		PreparedStatement prepStmt;
		ResultSet resultSet;
		try {
			checkStmt = connection.createStatement();
			
			prepStmt = connection.prepareStatement("INSERT INTO " + CAR_TABLE + " " +
					                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			prepStmt.setString(1, c.getMake());
			prepStmt.setString(2, c.getCountry());
			prepStmt.setString(3, c.getVIN());
			prepStmt.setString(4, c.getModel());
			prepStmt.setInt(5, c.getYear());
			prepStmt.setString(6, c.getSize());
			prepStmt.setString(7, c.getBodyType());
			prepStmt.setString(8, c.getPlate());
			prepStmt.setString(9, c.getFuel());
			prepStmt.setString(10, c.getTransmission());
			prepStmt.setInt(11, c.getDoor());
			prepStmt.setInt(12, c.getPassenger());
			prepStmt.setString(13, c.getColor());
			prepStmt.setShort(14, (short)((c.hasAC()) ? 1 : 0));
			prepStmt.setInt(15, c.getMPG());
			prepStmt.setShort(16, (short)((c.isSunroof()) ? 1 : 0));
			prepStmt.setShort(17, (short)((c.isConvertible()) ? 1 : 0));
			prepStmt.setString(18, c.getCondition());
			prepStmt.setInt(19, c.getMSRP());
			prepStmt.setInt(20,c.getRentalPrice());
			prepStmt.setShort(21, (short)((c.isRented()) ? 1 : 0));
			prepStmt.setInt(22, c.getLocationID());
			prepStmt.setBlob(23, c.getImageBlob());
			
			resultSet = checkStmt.executeQuery("SELECT * " +
					                            "FROM " + CAR_TABLE + " " +
					                            "WHERE " + CAR_VIN + " = \'" + c.getVIN() + "\'");
			
			if(!resultSet.next()) {
				prepStmt.executeUpdate();
			} else {
				return 1;
			}
			
			resultSet.close();
			checkStmt.close();
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		
		return 2;
	}
	
	public static int addTransaction(Transaction t) {
		Statement insertStmt;
		PreparedStatement prepStmt;
		ResultSet resultSet;
		try {
			insertStmt = connection.createStatement();
			prepStmt = connection.prepareStatement("INSERT INTO " + TRANSACTION_TABLE + " " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			prepStmt.setTimestamp(1, t.getStartDate());
			prepStmt.setTimestamp(2, t.getEndDate());
			prepStmt.setTimestamp(3, t.getPickupDate());
			prepStmt.setTimestamp(4, t.getDropoffDate());
			prepStmt.setString(5, t.getVin());
			prepStmt.setString(6, t.getAcctName());
			prepStmt.setInt(7, t.gettID());
			prepStmt.setInt(8, t.getUpfront());
			prepStmt.setInt(9, t.getPenalties());
			prepStmt.setInt(10, t.getPayID());
			prepStmt.setTimestamp(11, t.getTransactionDate());
			
			
			resultSet = insertStmt.executeQuery("SELECT * " +
					                            "FROM " + TRANSACTION_TABLE + " " +
					                            "WHERE " + TRANSACTION_ID + " = " + t.gettID());
			
			if(!resultSet.next()) {
				prepStmt.executeUpdate();
			} else {
				return 1;
			}
			
			prepStmt.close();
			resultSet.close();
			insertStmt.close();
			return 0;
			
		} catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		
		return 2;
	}
	
	public static int addPaymentOption(PaymentOption p) {
		Statement insertStmt;
		PreparedStatement prepStmt;
		ResultSet resultSet;
		try {
			insertStmt = connection.createStatement();
			prepStmt = connection.prepareStatement("INSERT INTO " + PAYMENT_OPTION_TABLE + " " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			prepStmt.setString(1, p.getPaytype());
			prepStmt.setInt(2, p.getPayID());
			prepStmt.setString(3, p.getAccountName());
			prepStmt.setString(4, p.getCardHolder());
			prepStmt.setString(5, p.getCardNumber());
			prepStmt.setString(6, p.getCardCompany());
			prepStmt.setInt(7, p.getCsv());
			prepStmt.setInt(8, p.getExpirationMonth());
			prepStmt.setInt(9, p.getExpirationYear());
			prepStmt.setShort(10, (short) ((p.isSelected()) ? 1 : 0));
			
			resultSet = insertStmt.executeQuery("SELECT * " +
					                            "FROM " + PAYMENT_OPTION_TABLE + " " +
					                            "WHERE " + PAYMENT_OPTION_ID + " = " + p.getPayID());
			
			if(!resultSet.next()) {
				prepStmt.executeUpdate();
			} else {
				return 1;
			}
			
			//resultSet.close();
			//insertStmt.close();
			prepStmt.close();
			return 0;
			
		} catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.getMessage();
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		
		return 2;
	}
	
	public static int updateAccount(String accountName, String column, String value) {
		Statement updateStmt;
		String updateStr = "UPDATE " + ACCOUNT_TABLE + " " +
				           "SET " + column + " = \'" + value + "\' " +
				           " WHERE " + ACCOUNT_NAME + " = \'" + accountName + "\'";
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(updateStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	
	public static int updateCar(Car c) {
		Statement checkStmt;
		PreparedStatement prepStmt;
		try {
			checkStmt = connection.createStatement();
			
			prepStmt = connection.prepareStatement("UPDATE " + CAR_TABLE + " " +
												   "SET " + CAR_MAKE + " = ?, " 
					                               		 + CAR_COUNTRY +  " = ?, "
					                               		 + CAR_MODEL +  " = ?, "
					                               		 + CAR_YEAR +  " = ?, "
					                               		 + CAR_SIZE +  " = ?, "
					                               		 + CAR_TYPE +  " = ?, "
					                               		 + CAR_PLATE +  " = ?, "
					                               		 + CAR_FUEL + " = ?, "
					                               		 + CAR_TRANSMISSION +  " = ?, "
					                               		 + CAR_DOORS +  " = ?, "
					                               		 + CAR_PASSENGERS +  " = ?, "
					                               		 + CAR_COLOR +  " = ?, "
					                               		 + CAR_AC +  " = ?, "
					                               		 + CAR_MPG +  " = ?, "
					                               		 + CAR_SUNROOF +  " = ?, "
					                               		 + CAR_CONVERTIBLE +  " = ?, "
					                               		 + CAR_CONDITION + " = ?, "
					                               		 + CAR_MSRP +  " = ?, "
					                               		 + CAR_RENTALPRICE +  " = ?, "
					                               		 + CAR_RENTED +  " = ?, "
					                               		 + CAR_LOCATION_ID +  " = ? " +
					                               	"WHERE " + CAR_VIN + " = " + "?");
			prepStmt.setString(1, c.getMake());
			prepStmt.setString(2, c.getCountry());
			prepStmt.setString(3, c.getModel());
			prepStmt.setInt(4, c.getYear());
			prepStmt.setString(5, c.getSize());
			prepStmt.setString(6, c.getBodyType());
			prepStmt.setString(7, c.getPlate());
			prepStmt.setString(8, c.getFuel());
			prepStmt.setString(9, c.getTransmission());
			prepStmt.setInt(10, c.getDoor());
			prepStmt.setInt(11, c.getPassenger());
			prepStmt.setString(12, c.getColor());
			prepStmt.setShort(13, (short)((c.hasAC()) ? 1 : 0));
			prepStmt.setInt(14, c.getMPG());
			prepStmt.setShort(15, (short)((c.isSunroof()) ? 1 : 0));
			prepStmt.setShort(16, (short)((c.isConvertible()) ? 1 : 0));
			prepStmt.setString(17, c.getCondition());
			prepStmt.setInt(18, c.getMSRP());
			prepStmt.setInt(19,c.getRentalPrice());
			prepStmt.setShort(20, (short)((c.isRented()) ? 1 : 0));
			prepStmt.setInt(21, c.getLocationID());
			prepStmt.setString(22, c.getVIN());
			
			prepStmt.executeUpdate();
			checkStmt.close();
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		
		return 2;
	}
	
	
	
	public static int updateAccount(Account acct) {
		PreparedStatement prepStmt;

		try {
			prepStmt = connection.prepareStatement("UPDATE " + ACCOUNT_TABLE + " " +
			                                        "SET " + ACCOUNT_FIRSTNAME + " = ?, "
		                                                   + ACCOUNT_LASTNAME + " = ?, "
		                                                   + ACCOUNT_PHONE_NUMBER + " = ?,"
		                                                   + ACCOUNT_EMAIL + " = ? " + 
		                                         " WHERE " + ACCOUNT_NAME + " = ? " );
			prepStmt.setString(1, acct.getFirstName());
			prepStmt.setString(2, acct.getLastName());
			prepStmt.setString(3, acct.getPhoneNumber());
			prepStmt.setString(4, acct.getEmail());
			prepStmt.setString(5, acct.getAccountName());
			
			prepStmt.executeUpdate();

			prepStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	
	public static int updateTransaction(int tID, String column, String value) {
		Statement updateStmt;
		String updateStr = "UPDATE " + TRANSACTION_TABLE + " " +
				           "SET " + column + " = " + value +
				           " WHERE " + TRANSACTION_ID + " = " + tID;
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(updateStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	public static int updatePaymentOption(int pID, String column, String value) {
		Statement updateStmt;
		String updateStr = "UPDATE " + PAYMENT_OPTION_TABLE + " " +
				           "SET " + column + " = " + value +
				           " WHERE " + PAYMENT_OPTION_ID + " = " + pID;
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(updateStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	
	
	
	public static Account checkPassword(String accountName, String password) {
		Statement selectStmt;
		ResultSet resultSet;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery("SELECT * " +
					                            "FROM "  + ACCOUNT_TABLE + " " +
					                            "WHERE " + ACCOUNT_NAME + " = \'" + accountName + "\'");
			
			if(!resultSet.next()) {
				return null;
			} 
			else {
				if(resultSet.getString(ACCOUNT_PASSWORD).equals(password)){
					return new Account(resultSet.getString(ACCOUNT_NAME), resultSet.getString(ACCOUNT_PASSWORD), 
									   resultSet.getString(ACCOUNT_FIRSTNAME), resultSet.getString(ACCOUNT_LASTNAME), 
									   resultSet.getString(ACCOUNT_PHONE_NUMBER), resultSet.getString(ACCOUNT_EMAIL),
									   resultSet.getBoolean(ACCOUNT_EMPLOYEE));
				}
			}
			
			resultSet.close();
			selectStmt.close();
		} catch (SQLException sqle) {
	//		sqle.printStackTrace();
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		
		return null;
	}
	
	public static int createAccount(Account acct) {
		Statement insertStmt;
		PreparedStatement prepStmt;
		ResultSet resultSet;
		
		try {
			insertStmt = connection.createStatement();
			prepStmt = connection.prepareStatement("INSERT INTO " + ACCOUNT_TABLE + " " +
					                               "VALUES (?, ?, ?, ?, ?, ?, ?)");
			
			prepStmt.setString(1, acct.getAccountName());
			prepStmt.setString(2, acct.getPassword());
			prepStmt.setString(3, acct.getFirstName());
			prepStmt.setString(4, acct.getLastName());
			prepStmt.setString(5, acct.getPhoneNumber());
			prepStmt.setString(6, acct.getEmail());
			prepStmt.setInt(7, (short)((acct.isEmployee()) ? 1 : 0));

			
			resultSet = insertStmt.executeQuery("SELECT * " +
					                           " FROM  " + ACCOUNT_TABLE +
					                           " WHERE " + ACCOUNT_NAME + " = \'" + acct.getAccountName() + "\'");
			
			if(!resultSet.next()) {
				prepStmt.executeUpdate();
			} else {
				return 1;
			}
			
			resultSet.close();
			insertStmt.close();
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		
		return 2;
	}
	
	public static int setRentalStatus(String vin, boolean rented) {
		int rentedStatus = (rented) ? 1 : 0;
		Statement updateStmt;
		String updateStr = "UPDATE " + CAR_TABLE + " " + 
				           "SET " + CAR_RENTED + " = " + rentedStatus + " " + 
				           "WHERE " + CAR_VIN + " = \'" + vin + "\'";
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(updateStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;

	}
	
	
	public static int deleteAccount(String accountName) {
		Statement updateStmt;
		String delStr = "DELETE " +
				        "FROM " + ACCOUNT_TABLE + " " + 
				        "WHERE " + ACCOUNT_NAME + " = \'" + accountName + "\'"; 
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(delStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	public static int deleteCar(String vin) {
		Statement updateStmt;
		String delStr = "DELETE " +
				        "FROM " + CAR_TABLE + " " +
				        "WHERE " + CAR_VIN + " = \'" + vin + "\'"; 
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(delStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	public static int removeAllCars() {
		Statement updateStmt;
		String delStr = "DELETE " +
				        "FROM " + CAR_TABLE; 
		try {
			updateStmt = connection.createStatement();
			updateStmt.executeUpdate(delStr);

			updateStmt.close();
			return 0;
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}

		return 1;
	}
	
	
	public static ArrayList<String> getStrEnum(String column, String table) {
		ArrayList<String> enums = new ArrayList<String>();
		Statement selectStmt;
		ResultSet resultSet;
		String sql = "SELECT " + column +
				    " FROM " + table + 
				    " GROUP BY " + column;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(sql);

			while (resultSet.next()) {
				enums.add(resultSet.getString(column));
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}
		
		return enums;
	}
	
	public static int getMax(String column, String table) {
		int max = Integer.MIN_VALUE;
		Statement selectStmt;
		ResultSet resultSet;
		String sql = "SELECT MAX(" + column + ") AS  maximum" +
				    " FROM " + table;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(sql);

			while (resultSet.next()) {
				max = resultSet.getInt("maximum");
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}
		
		return max;
	}
	
	public static int getMin(String column, String table) {
		int min = Integer.MAX_VALUE;
		Statement selectStmt;
		ResultSet resultSet;
		String sql = "SELECT MIN(" + column + ") AS minimum" +
				    " FROM " + table;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(sql);

			while (resultSet.next()) {
				min = resultSet.getInt("minimum");
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}
		
		return min;
	}
	
	public static Location getLocation(int locationID) {
		Location location = null;
		Statement selectStmt;
		ResultSet resultSet;
		String sql = "SELECT * "  +
				    " FROM " + LOCATION_TABLE + 
				    " WHERE " + LOCATION_ID + " = " + locationID;

		try {
			selectStmt = connection.createStatement();
			resultSet = selectStmt.executeQuery(sql);
			if(!resultSet.next()) {
				return null;
			} else {
			location = new Location(resultSet.getInt(LOCATION_ID), 
					                resultSet.getString(LOCATION_STATE), 
					                resultSet.getString(LOCATION_CITY), 
					                resultSet.getString(LOCATION_ADDRESS),
					                resultSet.getInt(LOCATION_ZIP));
			}
			resultSet.close();
			selectStmt.close();
		} 
		catch (SQLException sqle) {
			System.out.println("Error Msg: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLError: " + sqle.getErrorCode());
			System.out.println("Rollback the transaction and quit the program");
			System.out.println();
			try {
				connection.setAutoCommit(false);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			try {
				connection.rollback();
			} catch (Exception e) {
				JdbcException jdbcExc = new JdbcException(e, connection);
				jdbcExc.handle();
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException(QUERY_ERROR);
		}
		
		return location;
		
	}
	
}


class JdbcException extends Exception {

	private static final long serialVersionUID = 4496673825254655022L;
	Connection conn;

	public JdbcException(Exception e) {
		super(e.getMessage());
		conn = null;
	}

	public JdbcException(Exception e, Connection con) {
		super(e.getMessage());
		conn = con;
	}

	public void handle() {
		System.out.println(getMessage());
		System.out.println();

		if (conn != null) {
			try {
				System.out.println("--Rollback the transaction-----");
				conn.rollback();
				System.out.println("  Rollback done!");
			} catch (Exception e) {
			}
			;
		}
	}

	public void handleExpectedErr() {
		System.out.println();
		System.out
				.println("**************** Expected Error ******************\n");
		System.out.println(getMessage());
		System.out
				.println("**************************************************");
	}
}