package utils;

import java.util.ArrayList;

public class ArchetypeStorage {

    private final String use;

    public String getName() {
        return name;
    }

    private final String name;
    private final String misuse;
    private final String purpose;
    private final String elementType;
    private final String identifier;
    private ArrayList<String> choices;
    private ArrayList<String> ranges;
    private ArrayList<String> uses;

    /**
     * saves the values of an archetype xml file
     * @param path path to archetype
     * @throws Exception
     */
    public ArchetypeStorage(String path) throws Exception{
        XPathReader reader = new XPathReader(path);
        use = reader.getValue("use");
        misuse = reader.getValue("misuse");
        purpose = reader.getValue("purpose");
        identifier = reader.getIdentifier();
        elementType = reader.getElementType();
        choices = new ArrayList<>(reader.getChoices());
        uses = new ArrayList<>(reader.getUses());
        ranges = new ArrayList<>(reader.getRanges());
        name = reader.getName();
    }

    /**
     * @return the number of the archetypes stored in this class
     */
    public int size() {
        return uses.size() + 1;
    }

    /**
     * @return return true if the loaded archetype contains more than one
     *         archetype
     */
    public boolean usesSeveralArchetypes() {
        return uses.size() > 0;
    }

    public String getUse() {
        return use;
    }

    public String getMisuse() {
        return misuse;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getElementType() {
        return elementType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public ArrayList<String> getRanges() {
        return ranges;
    }

    public ArrayList<String> getUses() {
        return uses;
    }
}
