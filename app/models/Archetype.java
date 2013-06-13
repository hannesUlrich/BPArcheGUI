package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Archetype extends Model {

	@Id
	public String id;
	public String name;
	public String purpose;
	public String usage;
	public String misusage;
	@OneToMany(mappedBy="archetype", cascade=CascadeType.ALL)
	public List<Element> elements;

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

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Finder<String,Archetype> find = new Finder<String,Archetype>(
            String.class, Archetype.class
    );
	
	public String toString() {
		return id+" "+name+" "+elements;
	}
	
}
