package com.gridprobe.grid;

import com.gridprobe.grid.core.CoreGrid;
import com.gridprobe.grid.core.protocol.ip.udp.UDPProtocol;

public class Bootstrap {

	private static Grid grid;
	
	// FIXME too late to find something else... 0:14!!!
	public static Grid grid() throws GridException {
		synchronized(Bootstrap.class) {
			if (grid == null) {
				grid = CoreGrid.instance();
				grid.install(new UDPProtocol());
			}
			
			return grid;
		}
	}
}
