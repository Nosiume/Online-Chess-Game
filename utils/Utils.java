package utils;

public class Utils {

	public static String capitalize(String str)
	{
		String s1 = str.substring(0, 1).toUpperCase();
		return s1 + str.substring(1);
	}
	
}
