package utils;

import java.util.HashMap;

/**
 * class providing a possibility to get attributes and their values
 * 
 */
public class ChildElement {

	private HashMap<String, String> definitions;

	public ChildElement() {
		definitions = new HashMap<>();
	}

	public void addDefinition(String name, String value) {
		definitions.put(name, value);
	}

	public String getDefinition(String name) {
		return definitions.get(name);
	}
}
