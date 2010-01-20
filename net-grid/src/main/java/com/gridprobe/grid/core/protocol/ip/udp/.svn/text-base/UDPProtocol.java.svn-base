package com.gridprobe.grid.core.protocol.ip.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.GridException.Failure;
import com.gridprobe.grid.core.CoreProtocol;
import com.gridprobe.grid.core.protocol.ip.IPEndpoint;
import com.gridprobe.grid.core.protocol.ip.IPEndpoints;
import com.gridprobe.grid.serialization.ObjectSerializer;
import com.gridprobe.utils.SystemProperties;

public class UDPProtocol implements CoreProtocol {
	
	private static Logger logger = Logger.getLogger(UDPProtocol.class.getName());

    private static final String DEFAULT_GROUP = "230.31.32.33";
	private static final int DEFAULT_PORT = 2728;
	private static final int DEFAULT_PORT_WIDTH = 3;
	private static final Integer DEFAULT_MAX_PACKET_SIZE = 8192;

    private MulticastSocket serverSocket;
	private InetAddress group;
	private int[] ports;
	private int maxPacketSize;

	private UDPServer server;

	private Set<IPEndpoint> endpoints;;
	
	public UDPProtocol() throws GridException {
		initNetwork();
	}

	private void initNetwork() throws GridException {
		try {
			loadParams();
			loadPorts();
			openMulticastSocket();
			startReceiver();
			loadEndpoints();
		} catch (Exception e) {
			throw new GridException(Failure.NET_ERROR, e, "Error creating socket: "+e.getMessage());
		}
	}

	private void loadEndpoints() throws GridException {
		endpoints = IPEndpoints.onSocket(serverSocket);
	}

	private void loadParams() {
		maxPacketSize = loadMaxPacketSize();
	}

	private void loadPorts() {
		int port = loadBasePort();
		int width = loadPortWidth();

		ports = new int[width];
		for (int i=0; i<width; i++) {
			ports[i] = port+i;
		}
		
		logger.finest("UDP mounted on ports "+ports);
	}

	private void openMulticastSocket() throws IOException {

		MulticastSocket msock = null;
		for (int port: ports) {
			try {
				msock = new MulticastSocket(null);
				msock.setReuseAddress(true);
				msock.bind(new InetSocketAddress(port));
				break;
			}
			catch (IOException ex) {
				final String msg="Unable to open multicast socket on port "+port;
				logger.fine(msg);
				logger.log(Level.FINEST, msg, ex);
			}
		}
		
		if (msock == null)
			throw new IOException("Unable to open multicast socket on ports "+toString(ports));
		
		String groupAddressName = loadGroupAddressName();
		group = InetAddress.getByName(groupAddressName);
		msock.joinGroup(group);
		logger.finest("Joined group "+group);
		
		serverSocket = msock;
	}

	private void startReceiver() {
		server = new UDPServer(serverSocket, maxPacketSize);
		server.start();
	}

	@Override
	public boolean accepts(Endpoint endpoint) {
		return (endpoint instanceof IPEndpoint);
	}

	@Override
	public void addListener(Listener listener) {
		server.addListener(listener);
	}

	@Override
	public boolean broadcast(Message message) throws GridException {

		logger.info("Broadcasting message "+message);

		byte[] payload = getPayloadFor(message);
		
		for (int port: ports) {
			DatagramPacket packet = new DatagramPacket(
				payload, 
				payload.length,
				group, 
				port);
	
			try {
				serverSocket.send(packet);
				logger.finest("Broadcast packet sent to group "+group+" and port "+port);
			} catch (IOException ex) {
				throw new GridException(Failure.IO_ERROR, ex);
			}
		}
		
		return true;
	}

	@Override
	public Set<? extends Endpoint> endpoints() {
		return endpoints;
	}

	private byte[] getPayloadFor(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(maxPacketSize);
		ObjectSerializer.toBytes(buffer, message);
		byte[] payload = new byte[buffer.position()];
		buffer.flip();
		buffer.get(payload);
		return payload;
	}

	private int loadPortWidth() {
		return SystemProperties.getInt(this, "port_width", DEFAULT_PORT_WIDTH);
	}

	private int loadBasePort() {
		return SystemProperties.getInt(this, "port", DEFAULT_PORT);
	}

	private String loadGroupAddressName() {
		return SystemProperties.getString(this, "group", DEFAULT_GROUP);
	}

	private int loadMaxPacketSize() {
		return SystemProperties.getInt(this, "packet.maxsize", DEFAULT_MAX_PACKET_SIZE);
	}

	private static String toString(int[] ports) {
		StringBuffer sb = new StringBuffer();
		for (int b : ports) {
			if (sb.length() > 0)
				sb.append(',');
			sb.append(b);

		}
		return sb.toString();
	}

	@Override
	public boolean send(Message message, Endpoint to) throws GridException {
		throw new GridException(Failure.NET_ERROR, "Unsupported atm, sorry :)");		
	}
}

