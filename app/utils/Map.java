package utils;
import java.util.ArrayList;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class Map {
	private ArrayList<XMLEvent> events;
	private ArrayList<ArrayList<Attribute>> attributes;

	public Map() {
		events = new ArrayList<XMLEvent>();
		attributes = new ArrayList<ArrayList<Attribute>>();
	}

	public void addNewList(XMLEvent event, ArrayList<Attribute> attr) {
		events.add(event);
		attributes.add(attr);
	}

	public ArrayList<Attribute> getAttributes(int index) {
		if (index <= -1 || index >= attributes.size()) {
			return null;
		}
		return attributes.get(index);
	}

	public ArrayList<Attribute> getAttributes(XMLEvent e) {
		int i = -1;
		for (XMLEvent x : events) {
			i++;
			try {
				if (isEqual(e, x)) {
					break;
				}
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return attributes.get(i);
	}

	public XMLEvent getEvent(int index) {
		if (index <= -1 || index >= attributes.size()) {
			return null;
		}
		return events.get(index);
	}

	/**
	 * additional function to check whether two events are equal
	 * 
	 * @param ev1
	 *            event 1
	 * @param ev2
	 *            event 2
	 * @return returns true if both events have the same locations within the
	 *         xml file
	 * @throws Exception
	 */
	public boolean isEqual(XMLEvent ev1, XMLEvent ev2) throws Exception {
		boolean tmp = false;
		tmp = ev1.getLocation().getColumnNumber() == ev2.getLocation().getColumnNumber() && ev1.getLocation().getLineNumber() == ev2.getLocation().getLineNumber() && ev1.getLocation().getCharacterOffset() == ev2.getLocation().getCharacterOffset();
		return tmp;
	}

	public int size() {
		return events.size();
	}

	@Override
	public String toString() {
		return events.toString() + " = " + attributes.toString();
	}
}
