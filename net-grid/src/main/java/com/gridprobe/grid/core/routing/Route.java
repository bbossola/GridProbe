package com.gridprobe.grid.core.routing;

import java.nio.ByteBuffer;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.Agent.Id;
import com.gridprobe.grid.serialization.Binaryzable;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.ObjectSerializer;

public class Route implements Binaryzable {
	
	private Agent.Id to;
	private Endpoint thru;

	protected Route() {}
	
	public Route(Id to, Endpoint thru) {
		this.to = to;
		this.thru = thru;
	}
	
	public Agent.Id to() {
		return to;
	}
	
	public Endpoint thru() {
		return thru;
	}

	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		to = (Id) ObjectSerializer.fromBytes(buffer);
		thru = (Endpoint) ObjectSerializer.fromBytes(buffer);
	}

	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		ObjectSerializer.toBytes(buffer, to);
		ObjectSerializer.toBytes(buffer, thru);
	}
}
