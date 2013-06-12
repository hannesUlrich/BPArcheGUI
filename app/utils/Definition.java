package utils;

public class Definition extends ChildElement{

	private String localCode;
	
	public Definition(String localCode) {
		setLocalCode(localCode);
	}
	
	public String getLocalCode() {
		return localCode;
	}
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	
	@Override
	public String toString(){
		return "Code: " + localCode;
	}
}
