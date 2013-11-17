package com.gridprobe.grid.core.protocol.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.Lan;
import com.gridprobe.grid.serialization.BinaryzationException;

public class IPEndpoint implements Endpoint {

	private IPLan lan;
	private InetAddress host;
	private short port;

	// required for serialization support
	public IPEndpoint() {
	}

	public IPEndpoint(IPLan lan, InetAddress address, short port) {
		this.lan = lan;
		this.host = address;
		this.port = port;
	}
	
	@Override
	public Lan lan() {
		return lan;
	}
	
//	
//	public void send(Protocol protocol, Message message) throws NetworkException  {
//
//		byte[] payload = message.payload();
//        DatagramPacket packet = new DatagramPacket(
//        		payload, 
//        		payload.length, 
//        		host, 
//        		port);
//
//        try {
//        	((UDPProtocol)protocol).send(packet);
//        }
//        catch (IOException ex) {
//        	throw new NetworkException(Failure.IO_ERROR, ex);
//        }
//	}

	public String toString() {
		return host+":"+port+"@"+lan;
	}
	
	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		buffer.putShort(port);

		final byte[] ipaddr = host.getAddress();
		buffer.put((byte)ipaddr.length);
		buffer.put(ipaddr);
		
		lan.toBytes(buffer);
	}

	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		port = buffer.getShort();

		try {
			int size = (int)buffer.get();
			byte[] ipaddr = new byte[size];
			buffer.get(ipaddr);
			host = InetAddress.getByAddress(ipaddr);
			
			lan=new IPLan();
			lan.fromBytes(buffer);
		} catch (UnknownHostException e) {
			throw BinaryzationException.create(e);
		}
	}

	@Override
	public boolean equals (Object other) {
	    try {
	        return this.compareTo((IPEndpoint)other) == 0;
	    }
	    catch (Exception any) {
	        return false;
	    }
	}

	@Override
    public int compareTo(Endpoint ep) {
        return (ep == null ? 1 : toString().compareTo(ep.toString()));
    }

}
