import play.*;
import models.*;


/**
 * Created with IntelliJ IDEA.
 * User: Crassus
 * Date: 09.06.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        if(User.find.findRowCount()==0){
            new User("daniel", "1234", "Daniel Rehmann").save();
        }
    }
}
