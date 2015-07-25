package carrent.util;

public enum ComparatorOptionB {

	DESCENDING("Descending", -1),
	ASCENDING("Ascending", 1);
	
	private String name;
	private int value;
	
	private ComparatorOptionB(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public int getValue(){
		return value;
	}
}
