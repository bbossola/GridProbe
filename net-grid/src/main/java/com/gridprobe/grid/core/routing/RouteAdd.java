package com.gridprobe.grid.core.routing;

import java.nio.ByteBuffer;

import com.gridprobe.grid.Credentials;
import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.Agent.Id;
import com.gridprobe.grid.core.messages.CoreMessage;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.ObjectSerializer;

public class RouteAdd extends CoreMessage {

	private Endpoint source;
	private Route route;
	
	public RouteAdd(Id from, Id to, Credentials credentials, Endpoint source, Route route) {
		super(from, to, credentials);
		this.source = source;
		this.route = route;
	}

	public Endpoint source() {
		return source;
	}

	public Route route() {
		return route;
	}
	
	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		super.fromBytes(buffer);
		source = (Endpoint)ObjectSerializer.fromBytes(buffer);
		route = (Route)ObjectSerializer.fromBytes(buffer);
	}

	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		super.toBytes(buffer);
		ObjectSerializer.toBytes(buffer, source);
		ObjectSerializer.toBytes(buffer, route);
	}

}
