package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Archetype extends Model {

	private static final long serialVersionUID = 1L;
	@Id
	public String id;
	public String name;
	public String purpose;
	public String usage;
	public String misusage;
	
	@OneToMany
	public List<Element> elements;
	
	@ElementCollection
	public List<String> usedArchetypes;

	public Archetype(){}
	
	public Archetype(String id, String name, String purpose, String usage, String misusage) {
		this.id = id;
		this.name = name;
		this.purpose = purpose;
		this.usage = usage;
		this.misusage = misusage;
		elements = new ArrayList<Element>();
		usedArchetypes = new ArrayList<String>();
		save();
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
		update();
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
		update();
	}

	public String getMisusage() {
		return misusage;
	}

	public void setMisusage(String misusage) {
		this.misusage = misusage;
		update();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		update();
	}

	public List<Element> getElements() {
		return elements;
	}

	public void addElement(Element element) {
		elements.add(element);
		update();
	}
	
	public void setElements(List<Element> elements) {
		this.elements.addAll(elements);
		update();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		update();
	}

	public List<String> getUsedArchetypes() {
		return usedArchetypes;
	}

	public void setUsedArchetypes(List<String> usedArchetypes) {
		this.usedArchetypes = usedArchetypes;
		update();
	}
	
	public void addUsedArchetypeId(String id) {
		this.usedArchetypes.add(id);
		update();
	}

	public static Finder<String,Archetype> find = new Finder<String,Archetype>(
            String.class, Archetype.class
    );
	
	public String toString() {
		return id+" "+name+" "+elements;
	}
	
}
