package carrent.db;

import java.util.ArrayList;

import carrent.entity.Car;
import carrent.util.SearchOperator;

public class TestInterface {

	public static void main(String[] args) throws ClassNotFoundException {
		DBInterface db = new DBInterface();
		QueryBuilder qb = new QueryBuilder("*", "cars");
		qb.addInt("rentalprice", 1000, SearchOperator.LESS_EQUAL);
		ArrayList<Car> cars = db.queryCars(qb.toString());
		for(int i = 0; i < cars.size(); i++) {
			cars.get(i).printInfo(0);
		}
		db.closeConnection();
	}

}
