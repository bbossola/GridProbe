package com.gridprobe.grid;

import java.util.Set;


public interface Lan {

	public Set<? extends Agent> agents() throws GridException
	;
}
