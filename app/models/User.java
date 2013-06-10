package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Crassus
 * Date: 07.06.13
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User extends Model{
    @Id
    public String accountname;
    public String password;
    public String fullName;

    public User(String accountname, String password, String fullName) {
        this.accountname = accountname;
        this.password = password;
        this.fullName = fullName;
    }

    public static User authenticate(String accountname, String password){
        return find.where().eq("accountname",accountname).eq("password",password).findUnique();
    }
    public static Finder<String,User> find = new Finder<String,User>(
            String.class, User.class
    );

}
