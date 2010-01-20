package com.gridprobe.grid.core.actions;

import com.gridprobe.grid.GridException;
import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.core.CoreGrid;

public class JoiningAction extends AbstractAction {

	public JoiningAction() {
		super();
	}

	public JoiningAction(CoreCredentials from) {
		super(from);
	}

	@Override
	public void execute(CoreGrid grid) throws GridException {
		grid.agentRegistry().register(from.source());
		
		if (!grid.whoami().equals(from.source()))
			grid.broadcast(new PresenceAction(grid.credentials()));
	}
}
