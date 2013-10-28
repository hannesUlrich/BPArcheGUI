package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import utils.Helper;

public class XPathReader {
    private XPath xPath;
    private Document xmlDocument;
    private XPathExpression xPathExpression;
    private String identifier;

    public XPathReader(String filePath, String id,boolean execWithFile) throws  Exception{
        this(filePath);
        identifier = execWithFile ?  Helper.extractFileNameWithoutEnding(filePath) : id;
    }

    /**
     * constructor of the xml reader
     * uses xpath for parsing
     * @param path loads a xml file with the given path
     * @throws Exception throw any exception that occurs
     */
    public XPathReader(String path) throws  Exception{
        // loading the XML document from a file
        DocumentBuilderFactory builderfactory = DocumentBuilderFactory
                .newInstance();
        builderfactory.setNamespaceAware(true);

        DocumentBuilder builder = builderfactory.newDocumentBuilder();
        File file = new File(path);

        xmlDocument = builder.parse(file);

        XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
        xPath = factory.newXPath();
        identifier = Helper.extractFileNameWithoutEnding(path);
    }

    /**
     * performs xpath for getting the uses of an archetype
     * @return arraylist of string that contains the uses
     * @throws Exception
     */
    public ArrayList<String> getUses() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        xPathExpression = xPath.compile("//archetype[//concept[identifier='" + identifier + "']]/uses/use");
        NodeList nodeListBook = (NodeList) xPathExpression.evaluate(
                xmlDocument, XPathConstants.NODESET);

        for (int index = 0; index < nodeListBook.getLength(); index++) {
            String author = nodeListBook.item(index).getTextContent();
            list.add(author);
        }
        return list;
    }

    /**
     * performs xpath for getting the identifier of an archetype
     * @return returns the identifier
     * @throws Exception
     */
    public String getIdentifier() throws Exception{
        xPathExpression = xPath.compile("//archetype[concept/identifier='" + identifier + "']/concept/identifier");
        String value = xPathExpression.evaluate(
                xmlDocument, XPathConstants.STRING).toString();
        return value;
    }

    /**
     * performs xpath for getting the name / text of an archetype
     * @return returns the name
     * @throws Exception
     */
    public String getName() throws Exception{
        xPathExpression = xPath.compile("//archetype[concept/identifier='" + identifier + "']/concept/text");
        String value = xPathExpression.evaluate(
                xmlDocument, XPathConstants.STRING).toString();
        return value;
    }

    /**
     * performs xpath for getting a specific value of an archetype
     * @param tag enter misuse, use or purpose
     * @return returns the specific value
     * @throws Exception
     */
    public String getValue(String tag) throws Exception{
        xPathExpression = xPath.compile("//archetype[concept/identifier='" + identifier + "']/descendant::description/" + tag);
        String value = xPathExpression.evaluate(
                xmlDocument, XPathConstants.STRING).toString();
        return value;
    }

    /**
     * performs xpath for getting the element type of an archetype
     * @return returns the element type
     * @throws Exception
     */
    public String getElementType() throws Exception{
        xPathExpression = xPath.compile("//archetype[concept/identifier='" + identifier + "']/descendant::DataElement/@elementType");
        String value = xPathExpression.evaluate(
                xmlDocument, XPathConstants.STRING).toString();
        return value;
    }

    /**
     * performs xpath for getting the choices of an archetype
     * @return returns the choices or empty list
     * @throws Exception
    */
    public ArrayList<String> getChoices() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        xPathExpression = xPath.compile("//archetype[concept/identifier='" + identifier + "']/descendant::choices/item");
        NodeList nodeListBook = (NodeList) xPathExpression.evaluate(
                xmlDocument, XPathConstants.NODESET);

        for (int index = 0; index < nodeListBook.getLength(); index++) {
            String value = nodeListBook.item(index).getAttributes().item(nodeListBook.item(index).getAttributes().getLength()-1).getTextContent();
            if (value != null && !value.equals("")){
                list.add(value);
            }
        }
        return list;
    }

    /**
     * performs xpath for getting the ranges of an archetype
     * @return returns the ranges or empty list
     * @throws Exception
     */
    public ArrayList<String> getRanges() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        xPathExpression = xPath.compile("//archetype[concept/identifier='" + identifier + "']/descendant::choices/item/@range");
        NodeList nodeListBook = (NodeList) xPathExpression.evaluate(
                xmlDocument, XPathConstants.NODESET);
        for (int index = 0; index < nodeListBook.getLength(); index++) {
            String value = nodeListBook.item(index).getTextContent();
            list.add(value);
        }
        return list;
    }
}
