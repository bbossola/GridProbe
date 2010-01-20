package com.gridprobe.grid.core.actions;

import com.gridprobe.grid.GridException;
import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.core.CoreGrid;

public class DiscoveryAction extends AbstractAction {

	public DiscoveryAction() {
		super();
	}

	public DiscoveryAction(CoreCredentials from) {
		super(from);
	}

	@Override
	public void execute(CoreGrid grid) throws GridException {
		if (!grid.whoami().equals(from.source()))
			grid.broadcast(new PresenceAction(grid.credentials()));
	}
}
