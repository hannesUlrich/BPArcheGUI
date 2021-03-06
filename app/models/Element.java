package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Element extends Model {

	private static final long serialVersionUID = 1L;
	private static int count=1;
	@Id
	public int id;
	public String type;



    @ManyToOne(cascade=CascadeType.ALL)
	public Archetype archetype;



	@OneToMany
	public List<Choice> choices;

	public Element(Archetype arche, int name, String type, ArrayList<String> choices) {
		this.id = name;
		this.type = type;
		this.archetype = arche;
		this.choices = new ArrayList<>();
		for (String text : choices) {
			Choice temp = new Choice(count++,text,this);
			addChoice(temp);
		}
		save();
	}

	public Element(Archetype arche, int name, String type) {
		this.id = name;
		this.type = type;
		this.archetype = arche;
		save();
	}
    public Archetype getArchetype() {
        return archetype;
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

	public List<Choice> getChoices() {
		return choices;
	}

	public void addChoice(Choice theNewOne) {
		choices.add(theNewOne);
		update();
	}
	
	public void setChoices(int elementId ,ArrayList<Choice> choices) {
		Element element = Element.find.ref(elementId);
		element.choices.addAll(choices);
		element.update();
	}
	
	public static Finder<Integer,Element> find = new Finder<Integer,Element>(
            Integer.class, Element.class
    );
	
	public String toString() {
		if (choices == null ) {
			return String.valueOf(id) + " " + type;
		} else {
			String temp = String.valueOf(id) + " " + type + " ";
			List<Choice> databaseChoices = new ArrayList<Choice>();
			databaseChoices = Choice.findChoicebyElement(id);
			for (Choice choi : databaseChoices) {
				temp+= choi.getChoice() + " ";
			}
		return temp;
		}
	}
	
}
