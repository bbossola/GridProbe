package com.gridprobe.grid.core.actions;

import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.core.CoreGrid;

public class PresenceAction extends AbstractAction {

	public PresenceAction() {
		super();
	}

	public PresenceAction(CoreCredentials from) {
		super(from);
	}

	@Override
	public void execute(CoreGrid grid) {
		grid.agentRegistry().register(from.source());
	}
}
