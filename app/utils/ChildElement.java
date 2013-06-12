package utils;
import java.util.HashMap;


public class ChildElement {

	private HashMap<String,String> definitions;
	
	public ChildElement() {
		definitions = new HashMap<String, String>();
	}
	
	public void addDefinition(String name, String value){
		definitions.put(name, value);
	}
	
	public String getDefinition(String name){
		return definitions.get(name);
	}
}
