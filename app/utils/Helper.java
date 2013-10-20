package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.events.XMLEvent;
import models.Archetype;
import models.Element;

/**
 * This class contains helper methods which are used by the controller
 */
public class Helper {
//    /**
//     * additional function to check whether two events are equal
//     *
//     * @param ev1
//     *            event 1
//     * @param ev2
//     *            event 2
//     * @return returns true if both events have the same locations within the
//     *         xml file
//     * @throws Exception
//     */
//    public static boolean isEqual(XMLEvent ev1, XMLEvent ev2) throws Exception {
//        boolean tmp = false;
//        tmp = (ev1.getLocation().getColumnNumber() == ev2.getLocation()
//                .getColumnNumber())
//                && (ev1.getLocation().getLineNumber() == ev2.getLocation()
//                .getLineNumber())
//                && (ev1.getLocation().getCharacterOffset() == ev2.getLocation()
//                .getCharacterOffset());
//        return tmp;
//    }

    /**
     * @param aPath
     *            the file path
     * @return returns the name of a file (with extension)
     */
    public static String extractFileName(String aPath) {
        File aFile = new File(aPath);
        return aFile.getName();
    }

    /**
     * @param aPath
     *            the file path
     * @return returns the name of a file (without extension)
     */
    public static String extractFileNameWithoutEnding(String aPath) {
        File aFile = new File(aPath);
        String tmp = aFile.getName();
        return tmp.substring(0, tmp.lastIndexOf("."));
    }

    /**
     * @param aPath
     *            path to specific file
     * @return returns the file path of a file
     */
    public static String extractFilePath(String aPath) {
        File aFile = new File(aPath);
        return includeTrailingBackslash(aFile.getParent());
    }

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

    /**
     * returns the list representation of an iterator
     *
     * @param i
     * @return
     */
    public static <E> ArrayList<E> getList(Iterator<E> i) {
        ArrayList<E> list = new ArrayList<>();
        while (i.hasNext()) {
            list.add(i.next());
        }
        return list;
    }

    /**
     * @param aPath
     *            path to specific file
     * @return return true if file exists
     */
    public static boolean isFileExistent(String aPath) {
        File aFile = new File(aPath);
        return aFile.exists();
    }

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

    /**
     * Capitalizes the first letter of a string
     *
     * @param word
     * @return
     */
    public static String capitalizeBegining(String word) {
        return word.replaceFirst(word.substring(0, 1),
                String.valueOf(Character.toUpperCase(word.charAt(0))));
    }

    /**
     *
     * @param name
     * @return
     */
    public static String getArcheName(String name) {
        int pos = name.indexOf("-",name.indexOf("-")+1)+1;
        return name.substring(pos, name.indexOf("."));
    }

    /**
     * returns all files of the given directory
     *
     * @param directory
     *            has to be a directory
     * @return returns an empty list if directory is a file
     */
    public static ArrayList<File> getFiles(File directory) {
        ArrayList<File> files = new ArrayList<File>();
        if (directory.isFile()) {
            return files;
        }
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                files.add(file);
            }
        }
        return files;
    }



	public static int decideWhichType(Archetype arche, int id, String type, ArrayList<String> choices,ArrayList<String> ranges) {
		if (type.equalsIgnoreCase("mtArchetype")) {
			Element e = new Element(arche, id, "archetype", choices,ranges);
			return e.id;
		} else if (type.equalsIgnoreCase("mtBoolean")) {
			Element e = new Element(arche, id, "boolean", choices,ranges);
			return e.id;
		} else if (type.equalsIgnoreCase("mtQuantity")) {
			Element e = new Element(arche, id, "int", choices,ranges);
			return e.id;
		} else if (type.equalsIgnoreCase("mtStringlist")) {
			Element e = new Element(arche, id, "stringlist", choices,ranges);
			return e.id;
        } else if (type.equalsIgnoreCase("mtString")) {
            Element e = new Element(arche, id, "string", choices,ranges);
            return e.id;
		} else {
			return 0;
		}
	}


	
}
