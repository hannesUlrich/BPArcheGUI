package utils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLReader {

	/**
	 * Returns the extension of a file including the dot
	 * 
	 * @param aPath
	 *            path to file
	 * @return extension
	 */
	public static String extractFileExt(String aPath) {
		return aPath.substring(aPath.lastIndexOf('.'), aPath.length());
	}

	public static <E> ArrayList<E> getList(Iterator<E> i) {
		ArrayList<E> list = new ArrayList<E>();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}

	private boolean error = false;

	private String filePath;

	/**
	 * constructor checking if the given file is a XML file
	 * 
	 * @param file
	 *            path to file, if the file's extension is not .xml no
	 *            calculation will be executed
	 */
	public XMLReader(String file) {
		if (!extractFileExt(file).equals(".xml")) {
			System.err.println("No XML file!");
			error = true;
			return;
		}
		filePath = file;
	}

	/**
	 * @return creates the XMLReader reading the xml file stored in filePath
	 *         returns null if there is no XML available
	 */
	public XMLEventReader createXMLReader() {
		if (error) {
			return null;
		}
		try {
			// create input factory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// setup a new event Reader after creating a file input stream to
			// read a xml file
			InputStream input = new FileInputStream(filePath);
			return inputFactory.createXMLEventReader(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Attribute getAttribute(String attrName, List<Attribute> attributes) {
		Attribute a = null;
		for (Attribute attr : attributes) {
			if (attr.getName().getLocalPart().equals(attrName)) {
				return attr;
			}
		}
		return a;
	}

	/**
	 * returns the specific attribute according to the element name and
	 * attribute name
	 * 
	 * @param elementName
	 * @param attrName
	 * @return specific Attribute
	 * @throws Exception
	 */
	public Attribute getAttribute(String elementName, String attrName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		// create an empty list
		Attribute attr = null;
		if (error) {
			return attr;
		}
		// iterate through all events
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			// check if event is node
			if (event.isStartElement()) {
				StartElement start = event.asStartElement();
				// get all attributes
				if (start.getName().getLocalPart().equals(elementName)) {
					attr = start.getAttributeByName(new QName(attrName));
				}
			}
		}
		return attr;
	}

	// TODO
	public Attribute getAttribute(XMLEvent e, String attrName) {
		Attribute a = null;
		if (e.isStartElement()) {
			StartElement s = e.asStartElement();
			Attribute tmp = s.getAttributeByName(new QName(attrName));
			if (tmp != null) {
				a = tmp;
			}
		}
		return a;
	}

	/**
	 * @param elementName
	 * @return Returns the number of attributes belonging to the element with
	 *         name elementName
	 * @throws Exception
	 */
	public int getAttributeNumber(String elementName) throws Exception {
		return getAttributes(elementName).size();
	}

	/**
	 * returns the attributes of an element
	 * 
	 * @param elementName
	 *            name of the element
	 * @return all attributes that belong to this element
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getAttributes(String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		// create an empty list
		Map attr = new Map();
		if (error) {
			return attr;
		}
		// iterate through all events
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			// check if event is node
			if (event.isStartElement()) {
				StartElement start = event.asStartElement();
				// get all attributes
				if (start.getName().getLocalPart().equals(elementName)) {
					attr.addNewList(event, getList(start.getAttributes()));
				}
			}
		}
		return attr;
	}

	/**
	 * !! NOTE !! Barely the same as getValue. Instead of return the string
	 * (value) you will get the CharacterEvent
	 * 
	 * Returns the CharacterEvent of the given element name
	 * 
	 * @param elementName
	 *            the name of the element
	 * @return iterates through all elements stopping by first element with name
	 *         elementName returning the value of this element
	 * @throws Exception
	 */
	public XMLEvent getCharacterByName(String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		XMLEvent event = null;
		if (error) {
			return null;
		}
		while (eventReader.hasNext()) {
			event = eventReader.nextEvent();
			// check if it is the right element
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
				// return next event (CharacterEvent)
				return eventReader.nextEvent();
			}
		}
		return event;
	}

	/**
	 * private function required for function getChildren
	 * 
	 * @param r
	 *            XMLEventReader to get next events
	 * @param elementName
	 *            element Name
	 * @return List of all children
	 * @throws Exception
	 */
	private List<XMLEvent> getChild(XMLEventReader r, String elementName) throws Exception {
		ArrayList<XMLEvent> attr = new ArrayList<XMLEvent>();
		int counter = 0;
		while (r.hasNext()) {
			// get next event
			XMLEvent event = r.nextEvent();
			if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equals(elementName)) {
					// if it has the same name like the parent, increase a
					// counter to add children
					counter++;
				}
				attr.add(event);
			}
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equals(elementName)) {
					if (counter >= 1) {
						// decrease the counter
						counter--;
						continue;
					}
					break;
				}
			}
		}
		return attr;
	}

	/**
	 * returns all children of an element (on all levels)
	 * 
	 * @param elementName
	 *            name of the element
	 * @return list of all children
	 * @throws Exception
	 */
	public ArrayList<XMLEvent> getChildren(String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		// create an empty list
		ArrayList<XMLEvent> attr = new ArrayList<XMLEvent>();
		if (error) {
			return attr;
		}
		while (eventReader.hasNext()) {
			// get next event
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				// if the parent's name if the wanted one
				if (event.asStartElement().getName().getLocalPart().equals(elementName)) {
					// add all children returns by subfunction
					attr.addAll(getChild(eventReader, elementName));
				}
			}
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equals(elementName)) {
					// break the iteration if the end is reached
					break;
				}
			}
		}
		return attr;
	}

	/**
	 * returns all children of an element (on all levels)
	 * 
	 * @param elementName
	 *            name of the element
	 * @return list of all children
	 * @throws Exception
	 */
	public ArrayList<XMLEvent> getChildren(XMLEvent e) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		// create an empty list
		ArrayList<XMLEvent> attr = new ArrayList<XMLEvent>();
		if (error) {
			return attr;
		}
		String elementName = null;
		while (eventReader.hasNext()) {
			// get next event
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				// if the parent's name if the wanted one
				if (isEqual(event, e)) {
					elementName = event.asStartElement().getName().getLocalPart();
					// add all children returns by subfunction
					attr.addAll(getChild(eventReader, event.asStartElement().getName().getLocalPart()));
				}
			}
			if (event.isEndElement()) {
				if (elementName == null) {
					continue;
				}
				if (event.asEndElement().getName().getLocalPart().equals(elementName)) {
					// break the iteration if the end is reached, comment this
					// if all children of elementName are wanted
					break;
				}
			}
		}
		return attr;
	}

	/**
	 * returns the StartEvent element that belongs to the elementName
	 * 
	 * @param elementName
	 *            name of the element
	 * @param hasAttributes
	 *            specifies whether you want to get an element with attributes
	 *            or not
	 * @return returns the XMLEvent with the specific conditions
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<XMLEvent> getElement(boolean hasAttributes,
			String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		ArrayList<XMLEvent> events = new ArrayList<XMLEvent>();
		XMLEvent event = null;
		if (error) {
			return null;
		}
		// iterate through all events
		while (eventReader.hasNext()) {
			// get the next event
			event = eventReader.nextEvent();
			// check if next event is candidate for the wanted event
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
				if (hasAttributes) {
					if (getList(event.asStartElement().getAttributes()).size() > 0) {
						events.add(event);
					}
				}
				else {
					if (getList(event.asStartElement().getAttributes()).size() == 0) {
						events.add(event);
					}
				}
			}
		}
		return events;
	}

	/**
	 * returns the StartEvent element that belongs to the elementName
	 * 
	 * @param elementName
	 *            name of the element
	 * @return returns the XMLEvent with the specific conditions
	 * @throws Exception
	 */
	public XMLEvent getElement(String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		XMLEvent event = null;
		if (error) {
			return null;
		}
		// iterate through all events
		while (eventReader.hasNext()) {
			// get the next event
			event = eventReader.nextEvent();
			// check if next event is candidate for the wanted event
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
				return event;
			}
		}
		return event;
	}

	/**
	 * returns the StartEvent element that belongs to the elementName
	 * 
	 * @param elementName
	 *            name of the element
	 * @param hasAttributes
	 *            specifies whether you want to get an element with attributes
	 *            or not
	 * @return returns the XMLEvent with the specific conditions
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public XMLEvent getElement(String elementName, boolean hasAttributes) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		XMLEvent event = null;
		if (error) {
			return null;
		}
		// iterate through all events
		while (eventReader.hasNext()) {
			// get the next event
			event = eventReader.nextEvent();
			// check if next event is candidate for the wanted event
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
				if (hasAttributes) {
					if (getList(event.asStartElement().getAttributes()).size() > 0) {
						return event;
					}
				}
				else {
					if (getList(event.asStartElement().getAttributes()).size() == 0) {
						return event;
					}
				}
			}
		}
		return event;
	}

	/**
	 * Returns all events that match the name of the element
	 * 
	 * @param elementName
	 *            the name of the element
	 * @return iterates through all elements adding all elements matching the
	 *         element name to the candidate list.
	 * @throws Exception
	 */
	public ArrayList<XMLEvent> getElementsByName(String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		ArrayList<XMLEvent> events = new ArrayList<XMLEvent>();
		if (error) {
			return events;
		}
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
				// add every event that matches the element name
				events.add(event);
			}
		}
		return events;
	}

	/**
	 * returns the StartEvent element that belongs to the elementName,
	 * attributeName and attributeValue. It returns ENDDOCUMENT if there is no
	 * such event
	 * 
	 * @param elementName
	 *            name of the element
	 * @param attributeName
	 *            name of the specific attribute
	 * @param attributeValue
	 *            value of the specific attribute
	 * @return returns the XMLEvent with the specific conditions
	 * @throws Exception
	 */
	public XMLEvent getEvent(String elementName, String attributeName,
			String attributeValue) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		XMLEvent event = null;
		if (error) {
			return null;
		}
		// iterate through all events
		while (eventReader.hasNext()) {
			// get the next event
			event = eventReader.nextEvent();
			// check if next event is candidate for the wanted event
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName)) {
				// prevents NullPointerExceptions
				Attribute tmp = event.asStartElement().getAttributeByName(new QName(attributeName));
				if (tmp != null && tmp.getValue().equals(attributeValue)) {
					return event;
				}
			}
		}
		return event;
	}

	/**
	 * private function required for function getFirstLevelChildren. Possibility
	 * to define the depth by setting the counter to a specific value
	 * 
	 * @param r
	 *            XMLEventReader to get next events
	 * @param elementName
	 *            element Name
	 * @return List of all children on first level
	 * @throws Exception
	 */
	private List<XMLEvent> getFirstLevelChild(XMLEventReader r,
			String elementName) throws Exception {
		ArrayList<XMLEvent> attr = new ArrayList<XMLEvent>();
		String startElementName = null;
		XMLEvent storedStart = null;
		int counter = 0;
		while (r.hasNext()) {
			XMLEvent event = r.nextEvent();
			if (event.isStartElement()) {
				// on first level?
				if (counter == 0) {
					// get the specific start element name
					startElementName = event.asStartElement().getName().getLocalPart();
					// increase counter so that children won't be added
					counter++;
					// save the startEvent
					storedStart = event;
				}
			}
			if (event.isEndElement()) {
				// end element's name == start element's name
				if (event.asEndElement().getName().getLocalPart().equals(startElementName)) {
					// add stored start event to list
					attr.add(storedStart);
					// decrease counter for next child
					counter--;
					continue;
				}
				// jump out of iteration
				if (event.asEndElement().getName().getLocalPart().equals(elementName)) {
					break;
				}
			}
		}
		return attr;
	}

	/**
	 * returns all children of an element (on first level)
	 * 
	 * @param elementName
	 *            name of the element
	 * @return list of all children
	 * @throws Exception
	 */
	public ArrayList<XMLEvent> getFirstLevelChildren(String elementName) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		// create an empty list
		ArrayList<XMLEvent> attr = new ArrayList<XMLEvent>();
		if (error) {
			return attr;
		}
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equals(elementName)) {
					// add all children returned by subfunction
					attr.addAll(getFirstLevelChild(eventReader, elementName));
				}
			}
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equals(elementName)) {
					break;
				}
			}
		}
		return attr;
	}

	// TODO
	public XMLEvent getRoot(XMLEvent e) throws Exception {
		XMLEventReader eventReader = createXMLReader();

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {

			}
		}
		XMLEvent ev = null;
		return ev;
	}

	/**
	 * returns the value of an XML element
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public String getValue(String event) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		if (error) {
			return null;
		}
		while (eventReader.hasNext()) {
			XMLEvent e = eventReader.nextEvent();
			// e.equals() does not work, isEqual checks position of both events
			// within XML file, returns true if they have the same locations
			if (e.isStartElement() && e.asStartElement().getName().getLocalPart().equals(event)) {
				XMLEvent ev = eventReader.nextEvent();
				if (ev.isCharacters()) {
					return ev.asCharacters().getData();
				}
			}
		}
		return null;
	}

	/**
	 * returns the value of an XML element
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public String getValue(XMLEvent event) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		if (error) {
			return null;
		}
		while (eventReader.hasNext()) {
			XMLEvent e = eventReader.nextEvent();
			// e.equals() does not work, isEqual checks position of both events
			// within XML file, returns true if they have the same locations
			if (isEqual(e, event)) {
				// get the character event
				XMLEvent ev = eventReader.nextEvent();
				if (ev.isCharacters()) {
					return ev.asCharacters().getData();
				}
			}
		}
		return null;
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

	/**
	 * @return returns true if the filepath does not belong to a xml file
	 */
	public boolean isErroneous() {
		return error;
	}

	// TODO
	public boolean isRoot(XMLEvent root, XMLEvent child) throws Exception {
		XMLEventReader eventReader = createXMLReader();
		// create an empty list
		if (error || !child.isStartElement() || !root.isStartElement()) {
			return false;
		}
		String elementName = null;
		while (eventReader.hasNext()) {

			// get next event
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				if (isEqual(event, root)) {
					elementName = event.asStartElement().getName().getLocalPart();
					continue;
				}
			}
			if (isEqual(event, child) && elementName != null) {
				return true;
			}
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equals(elementName)) {
					// break the iteration if the end is reached, comment this
					// if all children of elementName are wanted
					break;
				}
			}
		}
		return false;
	}

	/**
	 * gives the possibility to load another file that also has to be an xml
	 * file
	 * 
	 * @param file
	 *            path to file
	 */
	public void reloadFile(String file) {
		if (!extractFileExt(file).equals(".xml")) {
			System.err.println("No XML file!");
			error = true;
			return;
		}
		filePath = file;
	}

}
