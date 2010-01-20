package com.gridprobe.grid.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.Protocol;

public class CoreTransport {

	Set<CoreProtocol> protocols = new HashSet<CoreProtocol>();

	CoreTransport() {
	}

	CoreProtocol install(Protocol protocol) {
		if (!(protocol instanceof CoreProtocol))
			throw new IllegalArgumentException("Invalid protocol " + protocol.getClass().getName());

		final CoreProtocol proto = (CoreProtocol) protocol;
		protocols.add(proto);
		return proto;
	}

	public Set<? extends Protocol> list() {
		return Collections.unmodifiableSet(protocols);
	}

	public void broadcast(final Message message) throws GridException {
		for (CoreProtocol proto : protocols) {
			proto.broadcast(message);
		}
	}

	public Set<Endpoint> endpoints() {
		Set<Endpoint> endpoints = new HashSet<Endpoint>();
		for (CoreProtocol proto : protocols) {
			endpoints.addAll(proto.endpoints());
		}
		
		return endpoints;
	}

}
