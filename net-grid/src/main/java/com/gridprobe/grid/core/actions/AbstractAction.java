package com.gridprobe.grid.core.actions;

import com.gridprobe.grid.core.CoreAction;
import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.core.messages.AbstractMessage;

public abstract class AbstractAction extends AbstractMessage implements CoreAction {

	public AbstractAction() {
	}

	public AbstractAction(CoreCredentials from) {
		super(from);
	}

	@Override
	public String toString() {
		return "Action "+getClass().getSimpleName()+" from "+credentials().source();
	}
}
