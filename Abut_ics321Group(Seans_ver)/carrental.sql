DROP TABLE Location;

CREATE TABLE Location(
 locationID INTEGER NOT NULL,
 state VARCHAR(50),
 city VARCHAR(100),
 street_address VARCHAR(100),
 zip INTEGER,
 PRIMARY KEY(locationID));

DROP TABLE Cars;

CREATE TABLE Cars(
 make VARCHAR(50),
 country VARCHAR(100),
 vin CHARACTER(17) NOT NULL,
 model VARCHAR(50),
 year INTEGER,
 size VARCHAR(20),
 type VARCHAR(20),
 plate VARCHAR(7),
 fuel VARCHAR(10),
 transmission VARCHAR(10),
 doors INTEGER,
 passengers INTEGER,
 color VARCHAR(50),
 ac SMALLINT,
 mpg INTEGER,
 sunroof SMALLINT,
 convertible SMALLINT,
 condition VARCHAR(256),
 msrp INTEGER,
 rentalprice INTEGER,
 rented SMALLINT,
 locationID INTEGER,
 image BLOB,
 PRIMARY KEY (vin),
 FOREIGN KEY (locationID)
   REFERENCES Location 
     ON DELETE SET NULL);

DROP TABLE Account;

CREATE TABLE Account(
 acct_name VARCHAR(30) NOT NULL,
 password VARCHAR(30),
 first_name VARCHAR(50),
 last_name VARCHAR(50),
 phone_number VARCHAR(50),
 email_address VARCHAR(100),
 is_employee SMALLINT,
 PRIMARY KEY(acct_name));

DROP TABLE Billing_Address;

CREATE TABLE Billing_Address(
 acct_name VARCHAR(30),
 name VARCHAR(100),
 locationID INTEGER NOT NULL,
 state VARCHAR(50),
 city VARCHAR(100),
 street_address VARCHAR(100),
 zip INTEGER,
 selected SMALLINT,
 PRIMARY KEY (locationID),
 FOREIGN KEY (acct_name)
   REFERENCES Account
     ON DELETE CASCADE);

DROP TABLE Payment_Option;

CREATE TABLE Payment_Option(
 paytype VARCHAR(20),
 payID INTEGER NOT NULL,
 acct_name VARCHAR(30),
 card_holder VARCHAR(100),
 card_number VARCHAR(20),
 card_company VARCHAR(100),
 csv INTEGER,
 expiration_month INT,
 expiration_year INT,
 selected SMALLINT,
 PRIMARY KEY (payID),
 FOREIGN KEY (acct_name)
   REFERENCES Account
     ON DELETE CASCADE);

DROP TABLE Rental_Transaction;

CREATE TABLE Rental_Transaction(
 start_date TIMESTAMP,
 end_date TIMESTAMP,
 pickup_date TIMESTAMP,
 dropoff_date TIMESTAMP,
 vin CHARACTER(17),
 acct_name VARCHAR(30),
 tID INTEGER NOT NULL,
 upfront INTEGER,
 penalties INTEGER,
 payID INTEGER,
 transaction_date TIMESTAMP,
 PRIMARY KEY(tID),
 FOREIGN KEY (vin)
   REFERENCES Cars 
     ON DELETE SET NULL,
 FOREIGN KEY (acct_name)
   REFERENCES Account
     ON DELETE SET NULL,
 FOREIGN KEY (payID)
   REFERENCES Payment_Option
     ON DELETE SET NULL);


