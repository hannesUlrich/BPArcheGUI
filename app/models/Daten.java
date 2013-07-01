package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Daten extends Model {

	private static final long serialVersionUID = 1L;
	@Id
	public int id;
	public String userID;
	public String archetypeType;
	public String value;
	public String selected;
	
	public Daten(){}
	
	public Daten(int id, String userID, String archetypeType, String value, String selected) {
		this.id = id;
		this.userID = userID;
		this.archetypeType = archetypeType;
		this.value = value;
		this.selected = selected;
		save();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		update();
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
		update();
	}

	public String getArchetypeType() {
		return archetypeType;
	}

	public void setArchetypeType(String archetypeType) {
		this.archetypeType = archetypeType;
		update();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		update();
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
		update();
	}

	public static Finder<Integer, Daten> find = new Finder<Integer, Daten>(Integer.class,Daten.class);
}
