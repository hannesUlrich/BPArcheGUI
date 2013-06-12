package utils;
import java.util.ArrayList;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class Components {

	private XMLReader reader;
	private ArrayList<Module> archetypes;

	public static void main(String[] args) {
		Components c = new Components("E:\\Programme\\Eclipse\\workspace\\XML\\bmi.xml");
		try {
			Module m = c.getArchetype(0);
			System.out.println(m.getDataElement().get(0).getValue("elementType"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean usesSeveralArchetypes(){
		return archetypes.size() > 1;
	}
	
	public Module getArchetype(int index) throws Exception{
		if (index < 0 || index >= archetypes.size()){
			throw new Exception("index out of bounds: can't reach Archetype with index " + index + " archetype's list size: " + archetypes.size());
		}
		return archetypes.get(index);
	}
	
	public Module getArchetype(String name) throws Exception{
		for (Module m : archetypes){
			if (m.getIdentifier().equalsIgnoreCase(name)){
				return m;
			}
		}
		throw new Exception("No archetype with name " + name);
	}
	
	public Components(String path) {
		archetypes = new ArrayList<Module>();
		reader = new XMLReader(path);
		try {
			ArrayList<XMLEvent> eventList = reader.getElementsByName("archetype");
			ArrayList<XMLEvent> identifierList = reader.getElementsByName("identifier");
			ArrayList<XMLEvent> defaultIdx = reader.getElementsByName("defaultIndex");
			ArrayList<XMLEvent> describtionList = reader.getElement(true, "description");
			ArrayList<XMLEvent> purposeList = reader.getElementsByName("purpose");
			ArrayList<XMLEvent> useList = reader.getElementsByName("use");
			ArrayList<XMLEvent> misuseList = reader.getElementsByName("misuse");
			ArrayList<XMLEvent> choiceList = reader.getChildren("choices");
			Map groups = reader.getAttributes("DataGroup");
			Map elements = reader.getAttributes("DataElement");
			ArrayList<XMLEvent> definitions = reader.getFirstLevelChildren("termDefinition");

			ArchetypeIdentifier id = new ArchetypeIdentifier();
			for (int i = 0; i < identifierList.size(); i++) {
				if (reader.isRoot(eventList.get(i), identifierList.get(i))) {
					id.addNewEvent(eventList.get(i), identifierList.get(i), reader.getValue(identifierList.get(i)));
				}
			}
			
			int moduleID = 0;
			for (XMLEvent archetype : eventList) {
				Module module = new Module(archetype);
				module.setId(moduleID);
				module.setIdentifier(id.getValue(moduleID++));

				XMLEvent tmpRoot = null;
				for (XMLEvent d : describtionList) {
					if (reader.isRoot(archetype, d)) {
						module.setLanguage(reader.getAttribute(d, "lang").getValue());
						tmpRoot = d;
					}
				}

				for (XMLEvent tmp : purposeList) {
					if (reader.isRoot(archetype, tmp)) {
						module.setPurpose(reader.getValue(tmp));
					}
				}
				
				for (XMLEvent d : useList) {
					if (reader.isRoot(archetype, d) && reader.isRoot(tmpRoot, d)) {
						module.setUse(reader.getValue(d));
					}
				}
				
				for (XMLEvent d : misuseList) {
					if (reader.isRoot(archetype, d)) {
						module.setMisuse(reader.getValue(d));
					}
				}
				
				ArrayList<String> choices = new ArrayList<String>();
				for (XMLEvent choiceEvent : choiceList) {
					if (reader.isRoot(archetype, choiceEvent)) {
						ArrayList<Attribute> list = XMLReader.getList(choiceEvent.asStartElement().getAttributes());
						for (Attribute a : list) {
							if (!a.getName().getLocalPart().equals("range")) {
								choices.add(a.getValue());
							}
						}
						module.addChoices(choices);
					}
				}
				
				for (XMLEvent defIdx : defaultIdx) {
					if (reader.isRoot(archetype, defIdx)) {
						module.setDefaultIndex(reader.getValue(defIdx));
					}
				}

				for (int i = 0; i < groups.size(); i++) {
					if (reader.isRoot(archetype, groups.getEvent(i))) {
						module.setGroups(groups.getAttributes(i));
					}
				}
				
				for (int i = 0; i < elements.size(); i++) {
					if (reader.isRoot(archetype, elements.getEvent(i))) {
						module.setDataElements(elements.getAttributes(i));
					}
				}
				
				ArrayList<XMLEvent> b;
				int i = 0, j = 0;
				for (XMLEvent d : definitions){
					if (reader.isRoot(archetype, d)){
						Definition x = new Definition(reader.getAttribute(d, "localCode").getValue());
						b = reader.getChildren(definitions.get(i));
						for (XMLEvent ev1 : b) {
							x.addDefinition(ev1.asStartElement().getName().getLocalPart(), 
									reader.getValue(b.get(j)));
							j++;
						}
						j = 0;
						i++;
						module.addDefinition(x);
					}
				}
				
				ArrayList<XMLEvent> boolSelection = reader.getChildren("boolean");
				if (boolSelection.size() > 0){
					for (XMLEvent ev1 : boolSelection) {
						ChildElement element = new ChildElement();
						element.addDefinition(ev1.asStartElement().getName().getLocalPart(), 
								reader.getValue(boolSelection.get(j)));
						j++;
						module.addChild(element);
					}
				}
				
				archetypes.add(module);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
