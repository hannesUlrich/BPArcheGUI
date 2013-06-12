package utils;
import java.util.ArrayList;

import javax.xml.stream.events.XMLEvent;

public class ArchetypeIdentifier {
	private ArrayList<XMLEvent> events;
	private ArrayList<XMLEvent> identifier;
	private ArrayList<String> values;

	public ArchetypeIdentifier() {
		identifier = new ArrayList<XMLEvent>();
		events = new ArrayList<XMLEvent>();
		values = new ArrayList<String>();
	}

	public void addNewEvent(XMLEvent event, XMLEvent id, String value) {
		events.add(event);
		identifier.add(id);
		values.add(value);
	}

	public XMLEvent getEvent(int index) {
		if (index <= -1 || index >= events.size()) {
			return null;
		}
		return events.get(index);
	}

	public XMLEvent getEvent(XMLEvent event) {
		return getEvent(event, false);
	}

	private XMLEvent getEvent(XMLEvent event, boolean wantIdentifier) {
		int i = -1;
		ArrayList<XMLEvent> list = new ArrayList<XMLEvent>();
		if (wantIdentifier) {
			list.addAll(identifier);
		}
		else {
			list.addAll(events);
		}
		for (XMLEvent x : list) {
			i++;
			try {
				if (isEqual(event, x)) {
					return list.get(i);
				}
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public XMLEvent getIdentifier(XMLEvent event) {
		return getEvent(event, true);
	}

	private int getIndex(XMLEvent event, boolean wantIdentifier) {
		int i = -1;
		ArrayList<XMLEvent> list = new ArrayList<XMLEvent>();
		if (wantIdentifier) {
			list.addAll(identifier);
		}
		else {
			list.addAll(events);
		}
		for (XMLEvent x : list) {
			i++;
			try {
				if (isEqual(event, x)) {
					return i;
				}
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	public String getValue(int index) {
		if (index <= -1 || index >= events.size()) {
			return null;
		}
		return values.get(index);
	}

	public String getValue(XMLEvent e, boolean useIdentifier) {
		int index = getIndex(e, useIdentifier);
		if (index == -1) {
			return null;
		}
		return values.get(index);
	}

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
		return events.toString() + " / " + identifier.toString() + " = " + values.toString();
	}
}
