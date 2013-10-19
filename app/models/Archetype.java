package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

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
	
    @OneToMany(cascade = CascadeType.ALL)
	public List<UsedArcheString> usedArchetypes;

	public Archetype(String id, String name, String purpose, String usage, String misusage) {
		this.id = id;
        this.name = name;
		this.purpose = purpose;
		this.usage = usage;
		this.misusage = misusage;
		elements = new ArrayList<>();
		usedArchetypes = new ArrayList<>();
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
		List<String> list =new ArrayList<>();
        for (UsedArcheString temp : usedArchetypes){
            list.add(temp.usedArchetype);
        }
        return list;
	}

	public void setUsedArchetypes(List<String> usedArchetypes) {
        for(String temp: usedArchetypes){
           this.usedArchetypes.add(new UsedArcheString(this,temp));
        }
        update();
	}
	
	public void addUsedArchetypeId(String archeId) {
        UsedArcheString test = new UsedArcheString(this,archeId);
        this.usedArchetypes.add(test);
		update();
	}

    public static Finder<String,Archetype> find = new Finder<String, Archetype>(String.class,Archetype.class);
	
	public String toString() {
		return id+" "+name+" "+elements;
	}
	
}
