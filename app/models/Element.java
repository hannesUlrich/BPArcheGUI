package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Element extends Model {

	private static final long serialVersionUID = 1L;
	@Id
	public int id;
	public String type;
	
	@ManyToOne
	public Archetype archetype;
	
	@ElementCollection
	private List<String> choices = new ArrayList<String>();

	public Element(Archetype arche, int name, String type, ArrayList<String> choices) {
		this.id = name;
		this.type = type;
		this.choices.addAll(choices);
		this.archetype = arche;
		save();
	}

	public Element(Archetype arche, int name, String type) {
		this.id = name;
		this.type = type;
		this.archetype = arche;
		save();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		update();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		update();
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices.addAll(choices);
		update();
	}
	
	public static Finder<Integer,Element> find = new Finder<Integer,Element>(
            Integer.class, Element.class
    );
	
	public String toString() {
		if (choices == null ) {
			return String.valueOf(id) + " " + type;
		} else {
		return String.valueOf(id) + " " + type + " " + choices.toString();
		}
	}
	
}
