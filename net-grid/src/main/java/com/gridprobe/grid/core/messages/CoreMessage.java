package com.gridprobe.grid.core.messages;

import java.nio.ByteBuffer;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Credentials;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.Agent.Id;
import com.gridprobe.grid.core.CoreAgent;
import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.ObjectSerializer;

public class CoreMessage implements Message {

	private Agent.Id from;
	private Agent.Id to;
	
	private Credentials credentials;
	
	public CoreMessage(Id from, Id to, Credentials credentials) {
		this.from = from;
		this.to = to;
		this.credentials = credentials;
	}

	@Override
	public Credentials credentials() {
		return credentials;
	}

	@Override
	public Id from() {
		return from;
	}

	@Override
	public Id to() {
		return to;
	}

	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		from = idFromBytes(buffer);
		to = idFromBytes(buffer);
		credentials = (CoreCredentials) ObjectSerializer.fromBytes(buffer);
	}

	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		idToBytes(buffer,from);
		idToBytes(buffer,to);
		ObjectSerializer.toBytes(buffer, (CoreCredentials)credentials);
	}

	private CoreAgent.Id idFromBytes(ByteBuffer buffer) {
		CoreAgent.Id id = new CoreAgent.Id(0);
		id.fromBytes(buffer);
		return id;
	}

	private void idToBytes(ByteBuffer buffer, Agent.Id id) {
		((CoreAgent.Id)id).toBytes(buffer);
	}
}
