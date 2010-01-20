package com.gridprobe.grid.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * A console that works, as on my jdk1.6 Runtime.getConsole() 
 * returns null and I'm now really pissed
 */
public class Console  {

	public static BufferedReader in;
	public static PrintStream out;
	public static PrintStream err;
	
	static {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = System.out;
		err = System.err;
	}
	
	private Console() {}
}
