package utils;

import java.util.ArrayList;

import javax.xml.stream.events.XMLEvent;

public class ArchetypeIdentifier {
	private ArrayList<XMLEvent> events;
	private ArrayList<XMLEvent> identifier;
	private ArrayList<String> values;

	/**
	 * every archetype has his own identifier tag. This class provides the
	 * ability to get this tag for an archetype
	 */
	public ArchetypeIdentifier() {
		identifier = new ArrayList<>();
		events = new ArrayList<>();
		values = new ArrayList<>();
	}

	/**
	 * adds a new event to the list
	 * 
	 * @param event
	 *            archetype
	 * @param id
	 *            archetypes identifier tag
	 * @param value
	 *            archetypes identifier tag's value
	 */
	public void addNewEvent(XMLEvent event, XMLEvent id, String value) {
		events.add(event);
		identifier.add(id);
		values.add(value);
	}

	/**
	 * returns the archetype event with index
	 * 
	 * @param index
	 * @return
	 */
	public XMLEvent getEvent(int index) {
		if ((index <= -1) || (index >= events.size())) {
			return null;
		}
		return events.get(index);
	}

	/**
	 * returns the archetype that is equal to event
	 * 
	 * @param event
	 * @return
	 */
	public XMLEvent getEvent(XMLEvent event) {
		return getEvent(event, false);
	}

	/**
	 * returns the archetype that is equal to event. if wantIdentifier is true,
	 * this method iterates through the identifier list (In this case youre
	 * looking for the id, otherwise the archetype)
	 * 
	 * @param event
	 * @param wantIdentifier
	 * @return
	 */
	private XMLEvent getEvent(XMLEvent event, boolean wantIdentifier) {
		int i = -1;
		ArrayList<XMLEvent> list = new ArrayList<>();
		if (wantIdentifier) {
			list.addAll(identifier);
		} else {
			list.addAll(events);
		}
		for (XMLEvent x : list) {
			i++;
			try {
				if (isEqual(event, x)) {
					return list.get(i);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * return the identifier to the archetype event
	 * 
	 * @param event
	 * @return
	 */
	public XMLEvent getIdentifier(XMLEvent event) {
		return getEvent(event, true);
	}

	/**
	 * gets the index of the archetype event
	 * 
	 * @param event
	 * @param wantIdentifier
	 * @return
	 */
	private int getIndex(XMLEvent event, boolean wantIdentifier) {
		int i = -1;
		ArrayList<XMLEvent> list = new ArrayList<>();
		if (wantIdentifier) {
			list.addAll(identifier);
		} else {
			list.addAll(events);
		}
		for (XMLEvent x : list) {
			i++;
			try {
				if (isEqual(event, x)) {
					return i;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	/**
	 * return archetypes value
	 * 
	 * @param index
	 * @return
	 */
	public String getValue(int index) {
		if ((index <= -1) || (index >= events.size())) {
			return null;
		}
		return values.get(index);
	}

	/**
	 * return archetypes value
	 * 
	 * @param e
	 * @param useIdentifier
	 * @return
	 */
	public String getValue(XMLEvent e, boolean useIdentifier) {
		int index = getIndex(e, useIdentifier);
		if (index == -1) {
			return null;
		}
		return values.get(index);
	}

	/**
	 * compares two events. if the location of both events within the xml
	 * document is equal this method returns true
	 * 
	 * @param ev1
	 * @param ev2
	 * @return
	 * @throws Exception
	 */
	public boolean isEqual(XMLEvent ev1, XMLEvent ev2) throws Exception {
		boolean tmp = false;
		tmp = (ev1.getLocation().getColumnNumber() == ev2.getLocation()
				.getColumnNumber())
				&& (ev1.getLocation().getLineNumber() == ev2.getLocation()
						.getLineNumber())
				&& (ev1.getLocation().getCharacterOffset() == ev2.getLocation()
						.getCharacterOffset());
		return tmp;
	}

	/**
	 * @return number of archetypes
	 */
	public int size() {
		return events.size();
	}

	@Override
	public String toString() {
		return events.toString() + " / " + identifier.toString() + " = "
				+ values.toString();
	}
}
