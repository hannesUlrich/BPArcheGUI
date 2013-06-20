package models;

import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Crassus
 * Date: 20.06.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class Choice extends Model{
    @Id
    public int id;
    public String choice;
    @OneToMany
    public Element element;


    public Choice(int id, String choice, Element element) {
        this.id = id;
        this.choice = choice;
        this.element = element;
    }

    public static Finder<Integer, Choice> find = new Finder<Integer, Choice>(Integer.class,Choice.class);

    public static List<Choice> findChoicebyElement(int id){
        return find.where().eq("elements.id", id).findList();
    }
}
