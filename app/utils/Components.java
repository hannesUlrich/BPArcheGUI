package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class Components {

	private XMLReader reader;
	private ArrayList<Module> archetypes;

	public static String extractFileName(String aPath) {
		File aFile = new File(aPath);
		String tmp = aFile.getName();
		return tmp.substring(0, tmp.lastIndexOf("."));
	}

	public static void main(String[] args) {
		File f = new File("E:\\Programme\\Eclipse\\workspace\\XML\\height.xml");
		Components c = new Components(
				"E:\\Programme\\Eclipse\\workspace\\XML\\height.xml");
		try {
			Module m = c.getArchetype(0);
			System.out
					.println(extractFileName("E:\\Programme\\Eclipse\\workspace\\XML\\height.xml"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return return true if the loaded archetype contains more than one
	 *         archetype
	 */
	public boolean usesSeveralArchetypes() {
		return archetypes.size() > 1;
	}

	/**
	 * This method provides the possibility of returning an archetype with id
	 * index
	 * 
	 * @param index
	 *            archetypes index
	 * @return specific Archetype specified in class Module
	 * @throws Exception
	 *             out of bound exception if index is out of range of archetypes
	 *             list
	 */
	public Module getArchetype(int index) throws Exception {
		if ((index < 0) || (index >= archetypes.size())) {
			throw new Exception(
					"index out of bounds: can't reach Archetype with index "
							+ index + " archetype's list size: "
							+ archetypes.size());
		}
		return archetypes.get(index);
	}

	/**
	 * This method provides the possibility of returning an archetype with
	 * archetypes name identifier
	 * 
	 * @param name
	 *            archetype's name
	 * @return specific Archetype specified in class Module
	 * @throws Exception
	 *             out of bound exception if index is out of range of archetypes
	 *             list
	 */
	public Module getArchetype(String name) throws Exception {
		for (Module m : archetypes) {
			if (m.getIdentifier().equalsIgnoreCase(name)) {
				return m;
			}
		}
		throw new Exception("No archetype with name " + name);
	}

	/**
	 * Constructor of components class. Loads all information about the given
	 * archetype defined in XML file
	 * 
	 * @param path
	 *            file path to archetype xml
	 */
	public Components(String path) {
		archetypes = new ArrayList<>();
		reader = new XMLReader(path);
		try {
			// Load lists with xmlreader
			ArrayList<XMLEvent> eventList = reader
					.getElementsByName("archetype");
			ArrayList<XMLEvent> identifierList = reader
					.getElementsByName("identifier");
			ArrayList<XMLEvent> defaultIdx = reader
					.getElementsByName("defaultIndex");
			ArrayList<XMLEvent> describtionList = reader.getElement(true,
					"description");
			ArrayList<XMLEvent> purposeList = reader
					.getElementsByName("purpose");
			ArrayList<XMLEvent> useList = reader.getElementsByName("use");
			ArrayList<XMLEvent> misuseList = reader.getElementsByName("misuse");
			ArrayList<XMLEvent> choiceList = reader.getChildren("choices");
			Map groups = reader.getAttributes("DataGroup");
			Map elements = reader.getAttributes("DataElement");
			ArrayList<XMLEvent> definitions = reader
					.getFirstLevelChildren("termDefinition");

			// store the identifier tag for each archetype
			ArchetypeIdentifier id = new ArchetypeIdentifier();
			for (int i = 0; i < identifierList.size(); i++) {
				if (reader.isRoot(eventList.get(i), identifierList.get(i))) {
					id.addNewEvent(eventList.get(i), identifierList.get(i),
							reader.getValue(identifierList.get(i)));
				}
			}

			int moduleID = 0;
			// iterate through all archetypes
			for (XMLEvent archetype : eventList) {
				// create new archetype module
				Module module = new Module(archetype);
				// save id (integer data type) for archetype
				module.setId(moduleID);
				// get identifier tag for archetype and store this information
				module.setIdentifier(id.getValue(moduleID++));

				XMLEvent tmpRoot = null;
				// get archetype's language
				for (XMLEvent d : describtionList) {
					if (reader.isRoot(archetype, d)) {
						module.setLanguage(reader.getAttribute(d, "lang")
								.getValue());
						tmpRoot = d;
					}
				}

				// get purpose description
				for (XMLEvent tmp : purposeList) {
					if (reader.isRoot(archetype, tmp)) {
						module.setPurpose(reader.getValue(tmp));
					}
				}

				// get use description
				for (XMLEvent d : useList) {
					if (reader.isRoot(archetype, d)
							&& reader.isRoot(tmpRoot, d)) {
						module.setUse(reader.getValue(d));
					}
				}

				// get misuse description
				for (XMLEvent d : misuseList) {
					if (reader.isRoot(archetype, d)) {
						module.setMisuse(reader.getValue(d));
					}
				}

				// get choices
				ArrayList<String> choices = new ArrayList<>();
				for (XMLEvent choiceEvent : choiceList) {
					if (reader.isRoot(archetype, choiceEvent)) {
						ArrayList<Attribute> list = XMLReader
								.getList(choiceEvent.asStartElement()
										.getAttributes());
						for (Attribute a : list) {
							if (!a.getName().getLocalPart().equals("range")) {
								choices.add(a.getValue());
							}
						}
						module.addChoices(choices);
					}
				}

				// get default choice index
				for (XMLEvent defIdx : defaultIdx) {
					if (reader.isRoot(archetype, defIdx)) {
						module.setDefaultIndex(reader.getValue(defIdx));
					}
				}

				// get all data groups
				for (int i = 0; i < groups.size(); i++) {
					if (reader.isRoot(archetype, groups.getEvent(i))) {
						module.setGroups(groups.getAttributes(i));
					}
				}

				// get all data elements
				for (int i = 0; i < elements.size(); i++) {
					if (reader.isRoot(archetype, elements.getEvent(i))) {
						module.setDataElements(elements.getAttributes(i));
					}
				}

				// get definitions and localcode
				ArrayList<XMLEvent> b;
				int i = 0, j = 0;
				for (XMLEvent d : definitions) {
					if (reader.isRoot(archetype, d)) {
						Definition x = new Definition(reader.getAttribute(d,
								"localCode").getValue());
						b = reader.getChildren(definitions.get(i));
						for (XMLEvent ev1 : b) {
							x.addDefinition(ev1.asStartElement().getName()
									.getLocalPart(), reader.getValue(b.get(j)));
							j++;
						}
						j = 0;
						i++;
						module.addDefinition(x);
					}
				}

				// get boolean data tags
				ArrayList<XMLEvent> boolSelection = reader
						.getChildren("boolean");
				if (boolSelection.size() > 0) {
					for (XMLEvent ev1 : boolSelection) {
						ChildElement element = new ChildElement();
						element.addDefinition(ev1.asStartElement().getName()
								.getLocalPart(),
								reader.getValue(boolSelection.get(j)));
						j++;
						module.addChild(element);
					}
				}

				archetypes.add(module);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
