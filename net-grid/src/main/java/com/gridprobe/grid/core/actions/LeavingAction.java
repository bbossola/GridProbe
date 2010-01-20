package com.gridprobe.grid.core.actions;

import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.core.CoreGrid;

public class LeavingAction extends AbstractAction {

	public LeavingAction() {
		super();
	}

	public LeavingAction(CoreCredentials from) {
		super(from);
	}

	@Override
	public void execute(CoreGrid grid) {
		grid.agentRegistry().unregister(from.source());
	}
}
