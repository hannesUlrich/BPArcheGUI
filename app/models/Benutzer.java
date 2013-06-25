package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Benutzer extends Model{

	private static final long serialVersionUID = 1L;
	@Id
    public String accountname;
    public String password;
    public String fullName;

    public Benutzer(String accountname, String password, String fullName) {
        this.accountname = accountname;
        this.password = password;
        this.fullName = fullName;
    }

    public static Benutzer authenticate(String accountname, String password){
        return find.where().eq("accountname",accountname).eq("password",password).findUnique();
    }
    public static Finder<String, Benutzer> find = new Finder<String, Benutzer>(
            String.class, Benutzer.class
    );

}
