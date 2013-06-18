package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class Components {

	private XMLReader reader;
	private ArrayList<Module> archetypes;
	private Module main;

	/**
	 * @return returns the current directory of execution
	 */
	public static String getCurrentDir() {
		File tmp = new File("");
		return includeTrailingBackslash(tmp.getAbsolutePath());
	}

	/**
	 * @param aPath
	 *            path to specific file
	 * @return the filepath with file separator at the end
	 */
	public static String includeTrailingBackslash(String aPath) {
		if (aPath.charAt(aPath.length() - 1) != System.getProperty(
				"file.separator").charAt(0)) {
			return aPath + System.getProperty("file.separator");
		}
		return aPath;
	}

	public static String extractFileName(String aPath) {
		File aFile = new File(aPath);
		String tmp = aFile.getName();
		return tmp.substring(0, tmp.lastIndexOf("."));
	}

	public static void main(String[] args) {
		ArrayList<Components> list = new ArrayList<>();
		Components c = new Components(
				"E:\\Programme\\Eclipse\\workspace\\XML\\height.xml");
		System.out.println(c.getMainModule());

		try {
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
			ArrayList<XMLEvent> uses = reader.getFirstLevelChildren("uses");

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

				ArrayList<String> tmpList = new ArrayList<>();
				for (XMLEvent tmp : uses) {
					if (reader.isRoot(archetype, tmp)) {
						tmpList.add(reader.getValue(tmp));
					}
				}
				if (!tmpList.isEmpty()) {
					module.addUses(tmpList);
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
				// save the main module of this module collection
				if (module.getIdentifier().equals(extractFileName(path))) {
					// main module is identified by the equality of identifier
					// and files name
					main = module;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get all archetypes that are used in the main archetype. if the archetype
	 * doesnt use any (other) archetype this function returns an empty list.
	 * 
	 * @return all uses
	 */
	public ArrayList<Module> getUses() {
		ArrayList<String> uses = main.getUses();
		ArrayList<Module> included = new ArrayList<>();
		for (Module m : archetypes) {
			// main archetype?
			if (m.getIdentifier().equals(main.getIdentifier())) {
				continue;
			}

			// archetype included in the archetype collection?
			if (uses.contains(m.getIdentifier())) {
				included.add(m);
			}
		}
		return included;
	}

	/**
	 * get all archetypes that are not included in the main archetype and the
	 * file relatively. You will get the archetypes identifier to load a new
	 * component.
	 * 
	 * @return returns an empty list if there are no archetypes left.
	 */
	public ArrayList<String> getNotIncluded() {
		ArrayList<String> uses = main.getUses();
		ArrayList<String> notIncluded = new ArrayList<>();
		for (Module m : archetypes) {
			if (m.getIdentifier().equals(main.getIdentifier())) {
				continue;
			}
			if (uses.contains(m.getIdentifier())) {
				continue;
			} else {
				notIncluded.add(m.getIdentifier());
			}
		}
		return notIncluded;
	}

	/**
	 * returns true if the main archetype contains all of its uses.
	 * 
	 * @return false if there are archetypes that are not within the archetypes
	 *         list
	 */
	public boolean hasAllUses() {
		ArrayList<String> uses = main.getUses();
		ArrayList<String> notIncluded = new ArrayList<>();
		for (Module m : archetypes) {
			if (m.getIdentifier().equals(main.getIdentifier())) {
				continue;
			}
			if (uses.contains(m.getIdentifier())) {
				continue;
			} else {
				notIncluded.add(m.getIdentifier());
			}
		}
		return notIncluded.isEmpty();
	}

	/**
	 * @return returns the archetype with the identifier that is equal to the
	 *         filename
	 */
	public Module getMainModule() {
		return main;
	}

	/**
	 * @return the number of the archetypes stored in this class
	 */
	public int size() {
		return archetypes.size();
	}
}
