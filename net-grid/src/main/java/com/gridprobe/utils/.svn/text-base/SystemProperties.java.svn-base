package com.gridprobe.utils;

/**
 * A simple helper class (sigh!) to read system properties using a class/object as 
 * a reference to the property name itself, including simpler method to get
 * default values if the property is missing
 * 
 * @author bossola
 */
public class SystemProperties {
	
	private SystemProperties () {}
	
	public static int getInt(Object from, String name, int defval) {
		
		final String propertyName = getClassName(from)+"."+name;
		return Integer.getInteger(propertyName, defval);
	}

	public static String getString(Object from, String name, String defval) {
		
		final String propertyName = from.getClass().getPackage().getName()+"."+name;
		return System.getProperty(propertyName, defval);
	}

	private static String getClassName(Object from) {
		Class<?> c;
		if (from instanceof Class<?>)
			c = (Class<?>) from;
		else
			c = from.getClass();
		
		return c.getName().toLowerCase();
	}
}
