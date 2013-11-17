package com.gridprobe.grid.core;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.GridException.Failure;
import com.gridprobe.grid.Lan;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.Protocol;
import com.gridprobe.grid.Report;
import com.gridprobe.grid.serialization.Binaryzable;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.ObjectSerializer;
import com.gridprobe.grid.util.UUID;

public class CoreAgent implements Agent, Binaryzable {

	private Id id;
	private Set<Endpoint> endpoints;
	private String nickname;
	
	public CoreAgent () {
		this(null, Collections.<Endpoint>emptySet());
	}

	public CoreAgent (String nickname, Set<Endpoint> endpoints) {
		this(new Id(UUID.generateLong()), nickname, endpoints);
	}

    CoreAgent (Id id, String nickname, Set<Endpoint> endpoints) {
        this.id=id;
        this.nickname=(nickname == null ? "" : nickname);
        this.endpoints = new TreeSet<Endpoint>(endpoints);
    }

	public Id id() {
		return id;
	}

	public Endpoint getEndpointFor(Protocol protocol) {
		for (Endpoint endpoint : endpoints) {
			if (protocol.accepts(endpoint))
				return endpoint;
		}
		
		return null;
	}

    @Override
	public int hashCode() {
        return id.hashCode();
	}
	
    @Override
	public boolean equals(Object o) {
		try {
			return this.id.equals(((CoreAgent)o).id);
		}
		catch (Exception any) {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(", ");
		sb.append(nickname);
		sb.append(" - endpoints: ");
		for (Endpoint ep : endpoints) {
			sb.append(ep.toString());
			sb.append("; ");
		}
		
		return sb.toString();
	}

	@Override
	public void toBytes(ByteBuffer buffer) {
		try {
			buffer.putLong(id.value());
			ObjectSerializer.toBytes(buffer, nickname);
			
			buffer.putShort((short)endpoints.size());
			for (Endpoint ep : endpoints) {
				ObjectSerializer.toBytes(buffer, ep);
			}
		}
		catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}

	@Override
	public void fromBytes(ByteBuffer buffer) {
		try {
			id = new Id(buffer.getLong());
			nickname = (String) ObjectSerializer.fromBytes(buffer);
			
			int num = buffer.getShort();
			endpoints = new TreeSet<Endpoint>();
			for (int i = 0; i < num; i++) {
				endpoints.add((Endpoint)ObjectSerializer.fromBytes(buffer));
			}
		}
		catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}

	public boolean isOn(Lan lan) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Endpoint> endpoints()  {
		return Collections.unmodifiableSet(endpoints);
	}

	@Override
	public Report send(Message message) throws GridException {
		// TODO Auto-generated method stub
		throw new GridException(Failure.NET_ERROR, "Sorry, unimplemented!");
	}

	@Override
	public Report send(Message message, MessageCallback callback)
			throws GridException {
		// TODO Auto-generated method stub
		throw new GridException(Failure.NET_ERROR, "Sorry, unimplemented!");
	}

	public static class Id implements Agent.Id, Binaryzable {
		private Long value;

		public Id(long value) {
			this.value = value;
		}

		public Long value() {
			return value;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
		
		@Override
		public int hashCode() {
			return value.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			try {
				final Long other = ((Id)obj).value;
                return this.value.equals(other);
			}
			catch (Exception ex) {
				return false;
			}
		}

		@Override
		public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
			value = (Long)buffer.getLong();
		}

		@Override
		public void toBytes(ByteBuffer buffer) throws BinaryzationException {
			buffer.putLong(value);
		}
	}
}
