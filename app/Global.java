import java.io.File;
import java.util.ArrayList;

import models.Archetype;
import models.Benutzer;
import models.Element;
import play.Application;
import play.GlobalSettings;
import utils.Helper;
import utils.ArchetypeStorage;
import utils.XPathReader;


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
            new Benutzer("daniel", "1234", "Daniel Rehmann", 0).save();
            new Benutzer("hannes", "1234", "Hannes Ulrich", 1).save();
        }
        if (Archetype.find.findRowCount() == 0) {
        	checkingArchetypes();
        }
    }

    public void loadInfileArchetypes(String identifier, String filepath) throws  Exception{
        XPathReader reader = new XPathReader(filepath,identifier,false);
        //String name = Helper.getArcheName(identifier);
        String purpose = reader.getValue("purpose");
        String usage = reader.getValue("use");
        String misusage = reader.getValue("misuse");
        Archetype arche = new Archetype( identifier, identifier, purpose, usage, misusage);
        Element ele = Element.find.byId(Helper.decideWhichType(arche ,count++, reader.getElementType(), reader.getChoices()));
        arche.addElement(ele);
        System.out.println("arche = " + arche);
    }

    public void checkingArchetypes() {
    	ArrayList<File> files = Helper.getFiles(new File(Helper.getCurrentDir()+"resource/"));
		for (File aFile : files) {
            try {
                ArchetypeStorage archeStorage = new ArchetypeStorage(Helper.getCurrentDir()+"resource/"+aFile.getName());
                String id = archeStorage.getIdentifier();
                String name = Helper.getArcheName(aFile.getName());
                String purpose = archeStorage.getPurpose();
                String usage = archeStorage.getUse();
                String misusage = archeStorage.getMisuse();
                Archetype arche = new Archetype( id, id, purpose, usage, misusage);
                Element eleTemp = Element.find.byId(Helper.decideWhichType(arche ,count++, archeStorage.getElementType(), archeStorage.getChoices()));
                Element ele = eleTemp;
                if (eleTemp == null){
                    int nameTmp = count - 1;
                    ele = new Element(arche,nameTmp,archeStorage.getElementType(),archeStorage.getChoices());

                }
                else if (eleTemp.type.equals("archetype")) {
					ArrayList<String> mods = archeStorage.getUses();
                    for (String s : mods) {
                        if (Archetype.find.byId(s) == null){
                            loadInfileArchetypes(s,Helper.getCurrentDir()+"resource/"+aFile.getName());
                        }
                        arche.addUsedArchetypeId(s);
                    }
				}
				arche.addElement(ele);
			} catch (Exception e) {
				System.err.println("failed to generate Archetype object: " + aFile.getName());
                e.printStackTrace();
			}
		}
    }
    
}
