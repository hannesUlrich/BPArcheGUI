package utils;

import java.util.ArrayList;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class Module {

	/**
	 * Checks if input string a is an Integer
	 * 
	 * @param a
	 *            input string
	 * @return true if the input string is an integer
	 */
	public static boolean isInteger(String a) {
		return a.matches("^-?[0-9]+$");
	}

	private XMLEvent archetypeRoot;
	private String use;
	private String misuse;
	private String purpose;
	private String language;
	private String identifier;
	private ArrayList<DataElement> groups;
	private ArrayList<DataElement> dataElement;
	private ArrayList<String> choices;
	private int defaultIndex;
	private ArrayList<ChildElement> booleanSelections;
	private ArrayList<Definition> definitions;

	private int id = 0;
	private ArrayList<String> uses;

	/**
	 * initialize all lists and variables
	 * 
	 * @param archetype
	 *            Module containing the archetype
	 */
	public Module(XMLEvent archetype) {
		setArchetypeRoot(archetype);
		groups = new ArrayList<>();
		dataElement = new ArrayList<>();
		choices = new ArrayList<>();
		definitions = new ArrayList<>();
		booleanSelections = new ArrayList<>();
		uses = new ArrayList<>();
	}

	/**
	 * add new child element (boolean data type)
	 * 
	 * @param e
	 */
	public void addChild(ChildElement e) {
		booleanSelections.add(e);
	}

	/**
	 * @return archetype has boolean choices
	 */
	public ArrayList<ChildElement> getBooleanSelections() {
		return booleanSelections;
	}

	/**
	 * adds new definition element to archetype
	 * 
	 * @param d
	 */
	public void addDefinition(Definition d) {
		definitions.add(d);
	}

	/**
	 * @return all definitions
	 */
	public ArrayList<Definition> getDefinitions() {
		return definitions;
	}

	/**
	 * adds all choices. if choices is empty no elements are in the field
	 * choices
	 * 
	 * @param choices
	 */
	public void addChoices(ArrayList<String> choices) {
		for (String s : choices) {
			if (this.choices.indexOf(s) < 0) {
				this.choices.add(s);
			}
		}
	}

	/**
	 * adds all uses as strings. To reference to module, store all components
	 * and try to find the module
	 * 
	 * @param choices
	 */
	public void addUses(ArrayList<String> uses) {
		for (String s : uses) {
			if (this.uses.indexOf(s) < 0) {
				this.uses.add(s);
			}
		}
	}

	/**
	 * @return returns all uses
	 */
	public ArrayList<String> getUses() {
		return uses;
	}

	/**
	 * creates a new element with a list of attributes
	 * 
	 * @param attr
	 *            list of attributes that belong to an element
	 * @return returns the created element
	 */
	private DataElement createElement(ArrayList<Attribute> attr) {
		DataElement e = new DataElement();
		ArrayList<String> attributes = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		for (Attribute a : attr) {
			attributes.add(a.getName().getLocalPart());
			values.add(a.getValue());
		}
		e.setAttributes(attributes, values);
		return e;
	}

	/**
	 * @return get root archetype
	 */
	public XMLEvent getArchetypeRoot() {
		return archetypeRoot;
	}

	/**
	 * @return returns all choices
	 */
	public ArrayList<String> getChoices() {
		return choices;
	}

	/**
	 * @return returns all data elements
	 */
	public ArrayList<DataElement> getDataElement() {
		return dataElement;
	}

	/**
	 * @return returns the default index of the choices
	 */
	public int getDefaultIndex() {
		return defaultIndex;
	}

	/**
	 * @return all DataGroups
	 */
	public ArrayList<DataElement> getGroup() {
		return groups;
	}

	/**
	 * @return the identifier tag
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the archetype's language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the misuse of the archetype
	 */
	public String getMisuse() {
		return misuse;
	}

	/**
	 * @return the purpose of the archetype
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @return archetype's use
	 */
	public String getUse() {
		return use;
	}

	/**
	 * sets the archetype root
	 * 
	 * @param archetypeRoot
	 */
	private void setArchetypeRoot(XMLEvent archetypeRoot) {
		this.archetypeRoot = archetypeRoot;
	}

	/**
	 * set all DataElements
	 * 
	 * @param attr
	 */
	public void setDataElements(ArrayList<Attribute> attr) {
		dataElement.add(createElement(attr));
	}

	/**
	 * sets default index for choices
	 * 
	 * @param index
	 */
	public void setDefaultIndex(String index) {
		int tmp = 0;
		if (isInteger(index)) {
			tmp = Integer.valueOf(index);
		}
		defaultIndex = tmp;
	}

	/**
	 * set description with all three values for use, misuse and purpse
	 * 
	 * @param use
	 * @param misuse
	 * @param purpose
	 */
	public void setDescription(String use, String misuse, String purpose) {
		setUse(use);
		setMisuse(misuse);
		setPurpose(purpose);
	}

	/**
	 * sets DataGroups
	 * 
	 * @param attr
	 */
	public void setGroups(ArrayList<Attribute> attr) {
		groups.add(createElement(attr));
	}

	/**
	 * sets identifier tags
	 * 
	 * @param identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * set language
	 * 
	 * @param lang
	 */
	public void setLanguage(String lang) {
		language = lang;
	}

	/**
	 * set misuse description
	 * 
	 * @param misuse
	 */
	public void setMisuse(String misuse) {
		this.misuse = misuse;
	}

	/**
	 * set purpose description
	 * 
	 * @param purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * set use description
	 * 
	 * @param use
	 */
	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * print archetype's identifier tag
	 */
	@Override
	public String toString() {
		return "ID: " + identifier;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets archetype's id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
