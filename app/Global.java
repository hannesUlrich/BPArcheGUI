import java.io.File;
import java.util.ArrayList;
import java.util.List;

import play.*;
import utils.*;
import models.*;


/**
 * Created with IntelliJ IDEA.
 * Benutzer: Crassus
 * Date: 09.06.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {
	
	public static int count;
	
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
    	ArrayList<File> files = Helper.checkFiles(new File(Helper.getCurrentDir()+"resource/"));
		for (File aFile : files) {
			Components comp = new Components(Helper.getCurrentDir()+"resource/"+aFile.getName());
			try {
				Module m = comp.getArchetype(Helper.extractFileNameWithoutEnding(aFile.getName()));
				String id = m.getIdentifier();
				String name = Helper.getArcheName(aFile.getName());
				String purpose = m.getPurpose();
				String usage = m.getUse();
				String misusage = m.getMisuse();
				Archetype arche = new Archetype( id, name, purpose, usage, misusage);
				List<Element> temp = new ArrayList<Element>();
				System.out.println("choi "+m.getChoices());
				System.out.println("ele " + m.getDataElement().get(0).getValue("elementType"));
				temp.add(Helper.decideWhichType(count, m.getDataElement().get(0).getValue("elementType"), m.getChoices()));
				count++;
				System.out.println(temp);
				arche.setElements(temp);
				arche.save();
			} catch (Exception e) {
				System.err.println("failed to generate Archetype object: " + aFile.getName());
			}
		}
    }
    
}
