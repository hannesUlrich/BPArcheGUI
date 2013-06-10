package models;

import org.junit.*;
import play.test.*;
import org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: Crassus
 * Date: 09.06.13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class UserTest extends WithApplication{

    @Before
    public void setUp() {
        start(Helpers.fakeApplication(Helpers.inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveUser() {
        new User("jupp", "1234", "Daniel Rehmann").save();
        User daniel = User.find.where().eq("accountname", "jupp").findUnique();
        Assert.assertNotNull(daniel);
        Assert.assertEquals("jupp", daniel.accountname);
    }
    @Test
    public void retrieveDaniel(){
        User daniel = User.find.where().eq("accountname", "daniel").findUnique();
        Assert.assertNotNull(daniel);
        Assert.assertEquals("daniel", daniel.accountname);
    }
    @Test
    public void allowDaniel(){
        User daniel = User.authenticate("daniel","1234");
        Assert.assertNotNull(daniel);
        Assert.assertEquals("daniel", daniel.accountname);
    }

}
