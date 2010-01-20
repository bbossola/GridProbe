package com.gridprobe.grid.console.commands;

import com.gridprobe.grid.console.Command;

public class ExitCommand implements Command {

	@Override
	public String description() {
		return "Exit";
	}

	@Override
	public void execute() throws Exception {
		System.out.println("\nExiting, thanks for all the fish :)");
		System.exit(0);
	}

}
