package com.gridprobe.grid.console.commands;

import java.util.Set;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Bootstrap;
import com.gridprobe.grid.Grid;
import com.gridprobe.grid.Lan;
import com.gridprobe.grid.console.Command;

public class GridStatusCommand implements Command {

	@Override
	public String description() {
		return "Grid status";
	}

	@Override
	public void execute() throws Exception {
		final Grid grid = Bootstrap.grid();

		Agent self = grid.whoami();
		System.out.println();
		System.out.println("  "+"Whoami: "+self);
		
		try {
			final Set<? extends Agent> agents = grid.agents();
			System.out.println("  "+"Agents: "+agents.size());
			for (Agent agent : agents) {
				System.out.println("    "+agent);
			}
	
			final Set<? extends Lan> lans = grid.lans();
			System.out.println("  "+"Lans: "+lans.size());
			for (Lan lan: lans) {
				System.out.println("    "+lan);
			}
		}
		finally {
			System.out.println();
		}
	}

}
