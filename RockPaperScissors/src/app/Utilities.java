package app;

public class Utilities {
	
	/**
	 * helper method that checks a given string for illegal characters or null value
	 * @param str
	 * @return false if the string is null or contains illegal characters, true otherwise
	 */
	public static boolean checkIllegal(String str) {
		if(str == null || str.equals("Add") || str.equals("Delete"))
			return false;
		else {
			for(int i = 0; i < str.length(); i++) {
				if(Driver.illegalCharacters.contains(str.charAt(i)))
					return false;
			}
		}
		return true;
	}
}
