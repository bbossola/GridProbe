package com.gridprobe.grid.console;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridprobe.grid.console.commands.ExitCommand;
import com.gridprobe.grid.console.commands.GridDiscoverCommand;
import com.gridprobe.grid.console.commands.GridJoinCommand;
import com.gridprobe.grid.console.commands.GridLeaveCommand;
import com.gridprobe.grid.console.commands.GridStatusCommand;
import com.gridprobe.grid.console.commands.InvalidCommand;
import com.gridprobe.grid.console.commands.LogLevelControlCommand;

public class Main {

	static {
		LogLevelControlCommand.setCurrentLevel(Level.WARNING);
	}
	
	private static final Logger logger = Logger.getLogger("com.gridprobe");
	
	public static Command[] commands = {
		new InvalidCommand(),
		new GridStatusCommand(),
		new GridJoinCommand(),
		new GridLeaveCommand(),
		new GridDiscoverCommand(),
		new LogLevelControlCommand(),
		new ExitCommand(),
	};
	
	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		
		while(true) {
			showMenu();
	
			try {command().execute();}
			catch (Exception ex) {
				System.err.println("Error: "+ex.getMessage());
				logger.log(Level.INFO, "An error occured!", ex);
			}

			sleep(500L);
		}
	}

	private Command command() throws IOException {
		String line = Console.in.readLine();
		
		Command result;
		try {
			int index = Integer.parseInt(line);
			result = commands[index];
		}
		catch (Exception ignore) {
			result = commands[0];
		}
				
		return result;
	}
	
	private void showMenu() {
		System.out.println();
		for (int i=1; i<commands.length; i++) {
			System.out.printf("%d) %s\n", i, commands[i].description());
		}

		System.out.printf("Action? ");
		System.out.flush();
	}

	private void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}
}
