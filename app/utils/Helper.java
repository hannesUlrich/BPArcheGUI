package utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import models.Element;

/**
 * This class contains helper methods which are used by the controller
 */
public class Helper {


	/**
	 * transforms a string array to an integer list
	 * @param tmp input String array
	 * @return List<Integer>
	 */
	public static List<Integer> convertToList(String[] tmp) {
		List<Integer> aList = new ArrayList<Integer>();
		for (String a: tmp) {
			if (isInteger(a)) {
				aList.add(StrToInt(a));
			}
		}
		return aList;
	}

	/**
	 * @param aPath the file path
	 * @return returns the name of a file (with extension)
	 */
	public static String extractFileName(String aPath){
		File aFile = new File(aPath);
		return aFile.getName();
	}

	public static String extractFileNameWithoutEnding(String aPath) {
		File aFile = new File(aPath);
		String tmp = aFile.getName();
		return tmp.substring(0, tmp.lastIndexOf("."));
		}
	
	/**
	 * @param aPath path to specific file
	 * @return returns the file path of a file
	 */
	public static String extractFilePath(String aPath){
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
	 * @param arr input array
	 * @return returns the highest element in an integer array
	 */
	public static int getMaxElement(int[] arr) {
		int tmp = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i]> tmp){
				tmp = arr[i];
			}
		}
		return tmp;
	}

	/**
	 * @param aPath path to specific file
	 * @return the filepath with file separator at the end
	 */
	public static String includeTrailingBackslash(String aPath) {
		if (aPath.charAt(aPath.length()-1) != System.getProperty("file.separator").charAt(0)) {
			return aPath + System.getProperty("file.separator");
		}
		return aPath;
	}

	/**
	 * Forms an integer a to a String
	 * @param a the given Integer
	 * @return String copy of the given Integer
	 */
	public static String IntToStr(int a){
		return String.valueOf(a);
	}


	/**
	 * Forms an long a to a String
	 * @param a the given long Integer
	 * @return String copy of the given long Integer
	 */
	public static String IntToStr(long a){
		return String.valueOf(a);
	}


	/**
	 * Forms an short Integer a to a String
	 * @param a the given short Integer
	 * @return String copy of the given short Integer
	 */
	public static String IntToStr(short a){
		return String.valueOf(a);
	}


	/**
	 * @param aPath path to specific file
	 * @return return true if file exists
	 */
	public static boolean isFileExistent(String aPath){
		File aFile = new File(aPath);
		return aFile.exists();
	}

	/**
	 * Checks if input string a is an Integer
	 * @param a input string
	 * @return true if match
	 */
	public static boolean isInteger(String a){
		return a.matches("^-?[0-9]+$");
	}

	/**
	 * Creates a random (integer) number between min and max
	 * 
	 * @param min
	 *            the minimum
	 * @param max
	 *            the maximum
	 * @return number between min and max
	 */
	public static int randomInt(int min, int max) {
		Random rd = new Random();
		if (min == max) {
			return min;
		}
		return min + rd.nextInt(max - min - 1);
	}

	

	/** Forms a String to an Integer
	 * @param a - the given Integer
	 * @return - the value of the given String
	 */
	public static int StrToInt(String a){
		return Integer.parseInt(a.trim());
	}

	/** Forms a String to an long Integer
	 * @param a - the given long Integer
	 * @return - the value of the given String
	 */
	public static long StrToLong(String a){
		return Long.parseLong(a.trim());
	}

	/** Forms a String to an short Integer
	 * @param a - the given short Integer
	 * @return - the value of the given String
	 */
	public static short StrToShort(String a){
		return Short.parseShort(a.trim());
	}
	
	public static Element decideWhichType(int id, String type, List<String> choices) {
		if (type.equalsIgnoreCase("mtBoolean")) {
			Element e = new Element(id, "boolean");
			return e;
		} else if (type.equalsIgnoreCase("mtQuantity")) {
			Element e = new Element(id, "int");
			e.setChoices(choices);
			return e;
		} else if (type.equalsIgnoreCase("mtStringlist")) {
			Element e = new Element(id, "mtStringlist");
			e.setChoices(choices);
			return e;
		} else {
			return null;
		}
	}
	
	public static String capitalBegining(String word) {
		return word.replaceFirst(word.substring(0, 1), String.valueOf(Character.toUpperCase(word.charAt(0))));
	}
	
	public static String getArcheName(String name) {
		return name.substring(10, name.indexOf("."));
	}
	
	public static ArrayList<File> checkFiles(File directory) {
		ArrayList<File> files = new ArrayList<File>();
		for (File file : directory.listFiles()) {
			files.add(file);
		}
		return files;
	}
	
}
