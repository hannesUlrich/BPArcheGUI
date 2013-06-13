package models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Element extends Model {

	@Id
	public int id;
	public String type;
	
	@ManyToOne
	public Archetype archetype;
	
	@ElementCollection
	private List<String> choices;

	public Element(int name, String type) {
		this.id = name;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	
	public static Finder<String,Element> find = new Finder<String,Element>(
            String.class, Element.class
    );
	
}
