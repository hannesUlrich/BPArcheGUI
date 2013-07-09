package utils;

import java.util.ArrayList;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class AttributeMap {
	private ArrayList<XMLEvent> events;
	private ArrayList<ArrayList<Attribute>> attributes;

	/**
	 * adds the ability to connect an element with its attributes
	 */
	public AttributeMap() {
		events = new ArrayList<>();
		attributes = new ArrayList<>();
	}

	/**
	 * add new event with all of its attributes
	 * 
	 * @param event
	 * @param attr
	 */
	public void addNewList(XMLEvent event, ArrayList<Attribute> attr) {
		if (!attr.isEmpty()) {
			events.add(event);
			attributes.add(attr);
		}
	}

	/**
	 * get attribute with index
	 * 
	 * @param index
	 * @return
	 */
	public ArrayList<Attribute> getAttributes(int index) {
		if ((index <= -1) || (index >= attributes.size())) {
			return null;
		}
		return attributes.get(index);
	}

	/**
	 * get all attributes of event
	 * 
	 * @param e
	 * @return
	 */
	public ArrayList<Attribute> getAttributes(XMLEvent e) {
		int i = -1;
		for (XMLEvent x : events) {
			i++;
			try {
				if (Helper.isEqual(e, x)) {
					return attributes.get(i);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @param index
	 * @return specific event with index
	 */
	public XMLEvent getEvent(int index) {
		if ((index <= -1) || (index >= attributes.size())) {
			return null;
		}
		return events.get(index);
	}

	public int size() {
		return events.size();
	}

	@Override
	public String toString() {
		return events.toString() + " = " + attributes.toString();
	}
}
