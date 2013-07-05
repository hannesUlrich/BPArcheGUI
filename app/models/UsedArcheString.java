package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: Crassus
 * Date: 03.07.13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class UsedArcheString extends Model{
    @Id
    public int id;
    @ManyToOne
    public Archetype archetype;
    public String usedArchetype;

    public UsedArcheString(Archetype archetype,String str){
        this.usedArchetype=str;
        this.archetype=archetype;
        save();
    }
}
