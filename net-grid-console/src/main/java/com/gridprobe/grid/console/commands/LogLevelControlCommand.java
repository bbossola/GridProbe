package com.gridprobe.grid.console.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridprobe.grid.console.Command;
import com.gridprobe.grid.console.Console;

public class LogLevelControlCommand implements Command {

	@Override
	public String description() {
		return "Change log level";
	}

	@Override
	public void execute() throws Exception {
		Console.out.printf("Current level: %s\n", toString(getCurrentLevel()));
		Console.out.printf("New level? [sev/war/inf/fin] ");
		
		String line = Console.in.readLine();
		Level level = fromString(line);
		
		if (level != null) {
			setCurrentLevel(level);
			Console.out.printf("New level: %s\n",toString(level));
		}
		else
			Console.out.printf("Level unchanged");
	}

	public static Level getCurrentLevel() {
		return Logger.getLogger("com.gridprobe").getLevel();
	}

	public static void setCurrentLevel(Level level) {
		Logger.getLogger("com.gridprobe").setLevel(level);
	}

	public String toString(Level level) {
		return level.getName();
	}
	
	public Level fromString(String s) {
		if (s == null)
			return null;

		s = s.trim();
		if (s.length() == 0)
			return null;
		
		s = s.toLowerCase();
		
		Level result;
		if (s.startsWith("sev"))
			result = Level.SEVERE;
		else if (s.startsWith("war"))
			result = Level.WARNING;
		else if (s.startsWith("inf"))
			result = Level.INFO;
		else 
			result = Level.FINEST;
		
		return result;
	}

}
