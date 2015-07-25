package carrent.db;

import carrent.util.SearchOperator;

/* Changelog:
 * 
 * 		Version 2.0: - CarQueryBuilder is now QueryBuilder and built to handle account
 * 					   related queries as well.
 * 
 * 		Version 2.1: - addInt now takes values from a SearchOperator enum instead of an
 * 					   integer when adding comparisons.
 */					   

/**
 * 
 * 
 * @version 2.1
 */
public class QueryBuilder {
	private StringBuilder builder;
	private int argc;
	
	public QueryBuilder(String select, String from) {
		builder = new StringBuilder("SELECT " + select +
				                    " FROM " + from +
				                    " WHERE");
		argc = 0;
	}
	
	public void addString(String type, String value) {
		builder.append(" " + type + "=\'" + value + "\' AND");
		argc++;
	}
	
	public void addInt(String type, int value, SearchOperator comparator) {
		String compval = "";
		
		switch(comparator.getValue()){
		case 0 : compval = " = "; break;
		case 1 : compval = " < "; break;
		case 2 : compval = " > " ; break;
		case 3 : compval = " <= "; break;
		case 4 : compval = " >= "; break;
		default : compval = " = ";
		}
		
		builder.append(" " + type + compval + value + " AND");
		argc++;
	}
	
	public void addBool(String type, boolean value) {
		int intval;
		if(value == true) intval = 1;
		else intval = 0;
		
		builder.append(" " + type + "=" + intval + " AND");
		argc++;
	}
	
	public String toString() {
		String str = builder.toString();
		str = str.replaceAll(" AND$", "");
		if(argc == 0)
		{
			str = str.replaceAll("WHERE$", "");
		}
		return str;
	}
}
