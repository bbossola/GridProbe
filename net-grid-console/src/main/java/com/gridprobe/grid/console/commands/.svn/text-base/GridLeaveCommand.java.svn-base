package com.gridprobe.grid.console.commands;

import com.gridprobe.grid.Bootstrap;
import com.gridprobe.grid.console.Command;

public class GridLeaveCommand implements Command {

	@Override
	public String description() {
		return "Leave the grid";
	}

	@Override
	public void execute() throws Exception {
		Bootstrap.grid().leave();
		System.out.println("Grid left!");
	}

}
