package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Element extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3245154473889537432L;
	@Id
	public int id;
	public String type;
	
	@OneToMany
	public Archetype archetype;
	
	@ElementCollection
	private List<String> choices;

	public Element(int name, String type) {
		this.id = name;
		this.type = type;
		choices = new ArrayList<String>();
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
		this.choices.addAll(choices);
	}
	
	public static Finder<String,Element> find = new Finder<String,Element>(
            String.class, Element.class
    );
	
	public String toString() {
		return String.valueOf(id) + " " + type + " " + choices.toString();
	}
	
}
