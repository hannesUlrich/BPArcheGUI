package models;

import java.util.List;

public class Archetype {

	private String id;
	private String name;
	private String purpose;
	private String usage;
	private String misusage;
	private List<Object> elements;

	public Archetype(String id, String name, String purpose, String usage, String misusage) {
		this.id = id;
		this.name = name;
		this.purpose = purpose;
		this.usage = usage;
		this.misusage = misusage;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getMisusage() {
		return misusage;
	}

	public void setMisusage(String misusage) {
		this.misusage = misusage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Object> getElements() {
		return elements;
	}

	public void setElements(List<Object> elements) {
		this.elements = elements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
