package utils;
import java.util.ArrayList;
import java.util.HashMap;

public class DataElement implements Comparable<DataElement>{
	public static int IDCount = 0;
	private HashMap<String, String> attributes;
	private int id;

	public DataElement() {
		attributes = new HashMap<String, String>();
		id = IDCount++;
	}

	public ArrayList<String> getAttributes() {
		return new ArrayList<String>(attributes.keySet());
	}

	public int getSize() {
		return attributes.size();
	}

	public String getValue(String attributeName) {
		return attributes.get(attributeName);
	}

	public void setAttributes(ArrayList<String> attributes,
			ArrayList<String> values) {
		for (int i = 0; i < attributes.size(); i++) {
			this.attributes.put(attributes.get(i), values.get(i));
		}
	}
	
	public int getId(){
		return id;
	}

	public boolean equals(DataElement o){
		return compareTo(o) == 0;
	}
	
	@Override
	public int compareTo(DataElement o) {
		if (id > o.getId()){
			return 1;
		}else if (id < o.getId()){
			return -1;
		}
		return 0;
	}
}
