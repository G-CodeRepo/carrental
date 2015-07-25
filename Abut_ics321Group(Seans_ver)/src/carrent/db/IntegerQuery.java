package carrent.db;
import carrent.util.SearchOperator;

public class IntegerQuery {

	private int target;
	private SearchOperator operator;
	
	public IntegerQuery(int target, SearchOperator operator){
		this.target = target;
		this.operator = operator;
	}
	
	public boolean match(int i){
		switch(operator){
			case EQUAL: return i == target;
			case GREATER: return i > target;
			case GREATER_EQUAL:return i >= target;
			case LESS: return i < target;
			case LESS_EQUAL: return i <= target;
			default : return false;
		}
	}
	
}
