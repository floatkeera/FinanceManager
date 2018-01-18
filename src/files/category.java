package files;


//object class for categories
public class category {
	//3 variables for this class
	String NAME;
	double VALUE;
	String month;
	
	//constructor
	public category(String _NAME, String _VALUE, String _month){
		NAME = _NAME;
		VALUE = Double.parseDouble(_VALUE);
		month = _month;
	}
	
	//string function to output string to be written to the data file
	public String toString() {

		String hello = NAME + ", " + VALUE + ", " + month;
		
		return hello;
	}
}

