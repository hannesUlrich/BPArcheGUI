package utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class storing information about the DataElements
 */
public class DataElement implements Comparable<DataElement> {
	public static int IDCount = 0;
	private HashMap<String, String> attributes;
	private int id;

	/**
	 * initialize the attribute hashmap adds a counter id to each new data
	 * element
	 */
	public DataElement() {
		attributes = new HashMap<>();
		id = IDCount++;
	}

	/**
	 * @return all attributes
	 */
	public ArrayList<String> getAttributes() {
		return new ArrayList<>(attributes.keySet());
	}

	/**
	 * @return number of attributes
	 */
	public int getSize() {
		return attributes.size();
	}

	/**
	 * gets value of the attribute
	 * 
	 * @param attributeName
	 * @return
	 */
	public String getValue(String attributeName) {
		return attributes.get(attributeName);
	}

	/**
	 * sets the values for each attributes
	 * 
	 * @param attributes
	 * @param values
	 */
	public void setAttributes(ArrayList<String> attributes,
			ArrayList<String> values) {
		for (int i = 0; i < attributes.size(); i++) {
			this.attributes.put(attributes.get(i), values.get(i));
		}
	}

	/**
	 * @return returns id
	 */
	public int getId() {
		return id;
	}

	/**
	 * compares this class to another DataElement class
	 * 
	 * @param o
	 * @return
	 */
	public boolean equals(DataElement o) {
		return compareTo(o) == 0;
	}

	@Override
	public int compareTo(DataElement o) {
		if (id > o.getId()) {
			return 1;
		} else if (id < o.getId()) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return attributes.toString();
	}
}
