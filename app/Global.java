import java.io.File;
import java.util.ArrayList;

import play.*;
import utils.*;
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
        if (Archetype.find.findRowCount() == 0) {
        	checkingArchetypes();
        }
    }
    
    public void checkingArchetypes() {
    	ArrayList<File> files = Helper.checkFiles(new File(Helper.getCurrentDir()+"resource/"));
		System.out.println();
		for (File aFile : files) {
			Components comp = new Components(Helper.getCurrentDir()+"resource/"+aFile.getName());
			try {
				Module m = comp.getArchetype(0);
				String id = m.getDataElement().get(0).getValue("id");
				String name = Helper.getArcheName(aFile.getName());
				String purpose = m.getDataElement().get(0).getValue("purpose");
				String usage = m.getDataElement().get(0).getValue("use");
				String misusage = m.getDataElement().get(0).getValue("misuse");
				Archetype arche = new Archetype( id, name, purpose, usage, misusage);
				ArrayList<Object> temp = new ArrayList<Object>();
				temp.add(Helper.decideWhichType(m.getDataElement().get(0).getValue("elementType")));
				arche.save();
			} catch (Exception e) {
				System.err.println("failed to generate Archetype objects");
			}
		}
    }
    
}
