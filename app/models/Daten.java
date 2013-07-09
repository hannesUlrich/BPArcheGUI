package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Daten extends Model {

	private static final long serialVersionUID = 1L;

    @Id
	public int id;
    @OneToOne
    public Archetype archetype;
	public String userID;
	public String value;
	public String selected;
	

    public void updateData(String value, String selected){
        this.value = value == null || value.equals("") ? "" : value;
        this.selected = selected == null || selected.equals("") ? "" : selected;
        update();
    }

	public Daten(String userID, Archetype arche, String value, String selected) {
		this.userID = userID;
		this.archetype = arche;
        this.value = value == null || value.equals("") ? "" : value;
		this.selected = selected == null || selected.equals("") ? "" : selected;
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

    @Override
    public String toString(){
        return userID+archetype.getId()+value+selected;
    }

    public Archetype getArchetype(){
        return archetype;
    }

	public static Finder<Integer, Daten> find = new Finder<Integer, Daten>(Integer.class,Daten.class);
}
