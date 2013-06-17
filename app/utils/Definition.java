package utils;

public class Definition extends ChildElement {

	private String localCode;

	/**
	 * adds new variable to the class based on childelement providing the
	 * storage of local codes for definitions
	 * 
	 * @param localCode
	 */
	public Definition(String localCode) {
		setLocalCode(localCode);
	}

	/**
	 * @return definition's local code
	 */
	public String getLocalCode() {
		return localCode;
	}

	/**
	 * set local code
	 * 
	 * @param localCode
	 */
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	@Override
	public String toString() {
		return "Code: " + localCode;
	}
}
