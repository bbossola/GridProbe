package com.gridprobe.grid.console.commands;

import com.gridprobe.grid.Bootstrap;
import com.gridprobe.grid.console.Command;

public class GridDiscoverCommand implements Command {

	@Override
	public String description() {
		return "Discovery";
	}

	@Override
	public void execute() throws Exception {
		Bootstrap.grid().discover();
		System.out.println("Discover request sent!");
	}

}
