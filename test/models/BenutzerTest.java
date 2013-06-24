package models;

import java.util.ArrayList;

import org.junit.*;
import play.test.*;

/**
 * Created with IntelliJ IDEA.
 * Benutzer: Crassus
 * Date: 09.06.13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class BenutzerTest extends WithApplication{

    @Before
    public void setUp() {
        start(Helpers.fakeApplication(Helpers.inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveUser() {
        new Benutzer("jupp", "1234", "Daniel Rehmann").save();
        Benutzer daniel = Benutzer.find.where().eq("accountname", "jupp").findUnique();
        Assert.assertNotNull(daniel);
        Assert.assertEquals("jupp", daniel.accountname);
    }
    @Test
    public void retrieveDaniel(){
        Benutzer daniel = Benutzer.find.where().eq("accountname", "daniel").findUnique();
        Assert.assertNotNull(daniel);
        Assert.assertEquals("daniel", daniel.accountname);
    }
    @Test
    public void allowDaniel(){
        Benutzer daniel = Benutzer.authenticate("daniel", "1234");
        Assert.assertNotNull(daniel);
        Assert.assertEquals("daniel", daniel.accountname);
    }
    
    @Test
    public void testChoices() {
    	String archeID = new Archetype("1", "", "", "", "").id;
    	ArrayList<String> choices = new ArrayList<String>();
    	choices.add("Mr.");
    	choices.add("Mrs.");
    	Element element = new Element(Archetype.find.byId(archeID), 1, "boolean", choices);
    }

}
