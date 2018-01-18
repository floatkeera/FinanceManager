package files;
public class transaction {

	String NAME;
	double VALUE;
	String NOTES;
	String DATE;
	String CATEGORY;

	public transaction(String _NAME, String _VALUE, String _NOTES,
			String _DATE, String _CATEGORY) {
		NAME = _NAME;
		VALUE = Double.parseDouble(_VALUE);
		NOTES = _NOTES;
		DATE = _DATE;
		CATEGORY = _CATEGORY;
	}

	public String toString() {

		String hello = NAME + ", " + VALUE + ", " + NOTES + ", " + DATE + ", "
				+ CATEGORY;

		return hello;
	}


}


