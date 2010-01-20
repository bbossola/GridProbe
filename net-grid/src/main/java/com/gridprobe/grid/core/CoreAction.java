package com.gridprobe.grid.core;

import com.gridprobe.grid.GridException;


public interface CoreAction {

	public abstract void execute(CoreGrid network) throws GridException
	;
}
