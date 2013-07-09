package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
