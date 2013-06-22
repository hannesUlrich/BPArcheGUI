package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;


@Entity
public class Choice extends Model{
   
	private static final long serialVersionUID = 1L;
	@Id
    public int id;
    public String choice;
    @ManyToOne
    public Element element;


    public Choice(String choice, Element element) {
        this.choice = choice;
        this.element = element;
        save();
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public String toString() {
		return element.toString() +  choice;
	}
	
	public static Finder<Integer, Choice> find = new Finder<Integer, Choice>(Integer.class,Choice.class);

    public static List<Choice> findChoicebyElement(int id){
        return find.where().eq("elements.id", id).findList();
    }
}
