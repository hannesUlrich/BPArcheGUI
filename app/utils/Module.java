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
	 * @return true if match
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

	public Module(XMLEvent archetype) {
		setArchetypeRoot(archetype);
		groups = new ArrayList<DataElement>();
		dataElement = new ArrayList<DataElement>();
		choices = new ArrayList<String>();
		definitions = new ArrayList<Definition>();
		booleanSelections = new ArrayList<ChildElement>();
	}

	public void addChild(ChildElement e){
		booleanSelections.add(e);
	}
	
	public ArrayList<ChildElement> getBooleanSelections() {
		return booleanSelections;
	}
	
	public void addDefinition(Definition d){
		definitions.add(d);
	}
	
	public ArrayList<Definition> getDefinitions() {
		return definitions;
	}

	public void addChoices(ArrayList<String> choices) {
		this.choices.addAll(choices);
	}

	private DataElement createElement(ArrayList<Attribute> attr) {
		DataElement e = new DataElement();
		ArrayList<String> attributes = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		for (Attribute a : attr) {
			attributes.add(a.getName().getLocalPart());
			values.add(a.getValue());
		}
		e.setAttributes(attributes, values);
		return e;
	}

	public XMLEvent getArchetypeRoot() {
		return archetypeRoot;
	}

	public ArrayList<String> getChoices() {
		return choices;
	}

	public ArrayList<DataElement> getDataElement() {
		return dataElement;
	}

	public int getDefaultIndex() {
		return defaultIndex;
	}

	public ArrayList<DataElement> getGroup() {
		return groups;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getLanguage() {
		return language;
	}

	public String getMisuse() {
		return misuse;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getUse() {
		return use;
	}

	private void setArchetypeRoot(XMLEvent archetypeRoot) {
		this.archetypeRoot = archetypeRoot;
	}

	public void setDataElements(ArrayList<Attribute> attr) {
		dataElement.add(createElement(attr));
	}

	public void setDefaultIndex(String index) {
		int tmp = 0;
		if (isInteger(index)) {
			tmp = Integer.valueOf(index);
		}
		defaultIndex = tmp;
	}

	public void setDescription(String use, String misuse, String purpose) {
		setUse(use);
		setMisuse(misuse);
		setPurpose(purpose);
	}

	public void setGroups(ArrayList<Attribute> attr) {
		groups.add(createElement(attr));
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setLanguage(String lang) {
		language = lang;
	}

	public void setMisuse(String misuse) {
		this.misuse = misuse;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setUse(String use) {
		this.use = use;
	}

	
	@Override
	public String toString(){
		return "ID: " + identifier;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
