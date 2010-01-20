package com.gridprobe.grid.core;

import java.util.Set;

import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.Protocol;

public interface CoreProtocol extends Protocol {

	public interface Listener {
		public void onMessage(Message message)
		;
	}

	public void addListener(Listener listener)
	;

	public Set<? extends Endpoint> endpoints()
	;


	public boolean broadcast(Message message) throws GridException
	;
	
	public boolean send(Message message, Endpoint to) throws GridException
	;
}
