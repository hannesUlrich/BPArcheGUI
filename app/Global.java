import java.io.File;
import java.util.ArrayList;

import play.*;

import models.Archetype;
import models.Benutzer;
import models.Element;
import play.Application;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http.Request;
import utils.Helper;
import utils.ArchetypeStorage;


/**
 * Created with IntelliJ IDEA.
 * Benutzer: Crassus
 * Date: 09.06.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {
	
	public static int count=1;
	
    @Override
    public void onStart(Application app) {
        if(Benutzer.find.findRowCount()==0){
            new Benutzer("daniel", "1234", "Daniel Rehmann").save();
        }
        if (Archetype.find.findRowCount() == 0) {
        	checkingArchetypes();
        }
    }
    
    public void checkingArchetypes() {
    	ArrayList<File> files = Helper.getFiles(new File(Helper.getCurrentDir()+"resource/"));
		for (File aFile : files) {
            //Components comp = new Components(Helper.getCurrentDir()+"resource/"+aFile.getName());
            try {
                ArchetypeStorage archeStorage = new ArchetypeStorage(Helper.getCurrentDir()+"resource/"+aFile.getName());
                //Module m = comp.getMainModule();
                String id = archeStorage.getIdentifier();
                String name = Helper.getArcheName(aFile.getName());
                String purpose = archeStorage.getPurpose();
                String usage = archeStorage.getUse();
                String misusage = archeStorage.getMisuse();
                Archetype arche = new Archetype( id, name, purpose, usage, misusage);
                Element ele = Element.find.byId(Helper.decideWhichType(arche ,count++, archeStorage.getElementType(), archeStorage.getChoices()));
				if (ele.type.equals("archetype")) {
					ArrayList<String> mods = archeStorage.getUses();
                    for (String s : mods) {
                        arche.addUsedArchetypeId(s);
					}
				}
				arche.addElement(ele);
			} catch (Exception e) {
				System.err.println("failed to generate Archetype object: " + aFile.getName());
			}
		}
    }
    
}
