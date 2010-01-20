package com.gridprobe.grid.console.commands;

import java.net.InetAddress;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Bootstrap;
import com.gridprobe.grid.console.Command;
import com.gridprobe.grid.console.Console;

public class GridJoinCommand implements Command {

	@Override
	public String description() {
		return "Join the grid";
	}

	@Override
	public void execute() throws Exception {

		Console.out.printf("Nickname? [enter for auto] ");
		
		String nickname = Console.in.readLine();
		if (nickname == null || nickname.trim().length() == 0) {
			final InetAddress localHost = InetAddress.getLocalHost();
			final String host = ((int)(Math.random()*100000))+"-"+localHost.getHostAddress();
			nickname = System.getProperty("user.name", "anonymous")+"@"+host;
		}
		
		Agent me = Bootstrap.grid().join(nickname);
		System.out.println("Grid joined! As agent "+me);
	}

}
