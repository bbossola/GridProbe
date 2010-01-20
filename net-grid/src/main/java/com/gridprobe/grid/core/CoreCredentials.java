package com.gridprobe.grid.core;

import java.nio.ByteBuffer;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Credentials;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.serialization.Binaryzable;
import com.gridprobe.grid.serialization.BinaryzationException;

public class CoreCredentials implements Credentials, Binaryzable {

	private CoreAgent source;

	public CoreCredentials() {}
	
	public CoreCredentials(CoreGrid grid) throws GridException {
		source = (CoreAgent)grid.whoami();
	}

	public Agent source() {
		return source;
	}
	
	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		source = new CoreAgent();
		source.fromBytes(buffer);
	}

	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		source.toBytes(buffer);
	}
	
	
}
