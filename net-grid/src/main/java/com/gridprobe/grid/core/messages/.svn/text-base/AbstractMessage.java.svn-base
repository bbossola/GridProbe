package com.gridprobe.grid.core.messages;

import java.nio.ByteBuffer;

import com.gridprobe.grid.Credentials;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.core.CoreCredentials;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.ObjectSerializer;

public abstract class AbstractMessage implements Message {

	protected CoreCredentials from;
	
	public AbstractMessage() {
	}

	public AbstractMessage(CoreCredentials from) {
		this.from = from;
	}

	@Override
	public Credentials credentials() {
		return from;
	}

	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		from = (CoreCredentials) ObjectSerializer.fromBytes(buffer);
	}

	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		ObjectSerializer.toBytes(buffer, from);
	}
	
	@Override
	public String toString() {
		return "Message "+getClass().getSimpleName()+" from "+credentials().source();
	}
}
