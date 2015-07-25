package carrent.util;

public enum SearchOperator {

	EQUAL("Equal to", 0),
	LESS("Less than", 1),
	GREATER("Greater than", 2),
	LESS_EQUAL("Less/equal", 3),
	GREATER_EQUAL("Greater/equal", 4);
	
	private String name;
	private int value;
	
	private SearchOperator(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public String toString(){
		return this.name;
	}
	
	public int getValue(){
		return this.value;
	}
}
