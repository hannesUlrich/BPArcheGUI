package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;


@Entity
public class Choice extends Model{
   
	private static final long serialVersionUID = 9217073710543188745L;
	@Id
    public int id;
    public String choiceString;
    @ManyToOne(cascade=CascadeType.ALL)
    public Element element;

    public Choice(){}
    
    public Choice(int id, String choice, Element element) {
    	this.id = id;
    	System.out.println("Im Konsrtuktor"+ choice);
        this.choiceString = choice;
        this.element = element;
        save();
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		update();
	}

	public String getChoice() {
		return choiceString;
	}

	public void setChoice(String choice) {
		this.choiceString = choice;
		update();
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
		update();
	}

	public String toString() {
		return element.toString() +  choiceString;
	}
	
	public static Finder<Integer, Choice> find = new Finder<Integer, Choice>(Integer.class,Choice.class);

    public static List<Choice> findChoicebyElement(int id){
        return find.where().eq("element.id", id).findList();
    }
}
