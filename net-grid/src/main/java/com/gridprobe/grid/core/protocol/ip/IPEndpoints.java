package com.gridprobe.grid.core.protocol.ip;

import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.gridprobe.grid.GridException;
import com.gridprobe.grid.GridException.Failure;

public class IPEndpoints  {
	
	public static Set<IPEndpoint> onSocket(MulticastSocket socket) throws GridException {
		
		Set<IPEndpoint> result = Collections.emptySet();
		
		try {
			
			NetworkInterface nic = socket.getNetworkInterface();
			if (isGlobal(nic)) {
				// bound on every interface
				result = onPort((short)socket.getLocalPort());
			} else {
				// bound on a real interface
				result = new HashSet<IPEndpoint>();
				collect(nic, result, (short)socket.getLocalPort());
			}
		}
		catch (SocketException ex) {
			throw new GridException(Failure.NET_ERROR, ex);
		}
		
		return result;
	}

	// FIXME: we need to find a better implementation!
	private static boolean isGlobal(NetworkInterface nic) throws SocketException {
//		return null == nic.getHardwareAddress();
		return null == nic.getDisplayName();
	}

	public static Set<IPEndpoint> onPort(short port) throws GridException {
		
		try {
			Set<IPEndpoint> endpoints = new HashSet<IPEndpoint>();
	        Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
	        for (NetworkInterface nic : Collections.list(nics)) {
				collect(nic, endpoints, port);
	        }
			return endpoints;
		}
		catch (SocketException ex) {
			throw new GridException(Failure.NET_ERROR, ex);
		}
	}
	
	private static void collect(NetworkInterface nic, Set<IPEndpoint> endpoints, short port) throws GridException {
		for (InterfaceAddress address : nic.getInterfaceAddresses()) {
	        if (!address.getAddress().isLoopbackAddress()) {
	        	IPEndpoint endpoint = new IPEndpoint(
	        			IPLan.createFrom(address),
	        			address.getAddress(),
	        			port
        			);
	        	
	        	endpoints.add(endpoint);
	        }
	    }

		Enumeration<NetworkInterface> subNic = nic.getSubInterfaces();
        for (NetworkInterface subIf : Collections.list(subNic)) {
        	collect(subIf, endpoints, port);
        }
	}
	
	public static void main(String[] args) throws Exception {
		
		int port = 0;
		MulticastSocket socket = new MulticastSocket(port );
		try {
			NetworkInterface nic = socket.getNetworkInterface();
			System.out.println("NIC:         "+nic);
			System.out.println("Name:        "+nic.getName());
			System.out.println("Addresses:   "+nic.getInetAddresses());
			System.out.println("DisplayName: "+nic.getDisplayName());
			System.out.flush();
//			System.out.println("HW addr:     "+nic.getHardwareAddress());
			
			Set<IPEndpoint> endpoints = onSocket(socket);
			for (IPEndpoint endpoint : endpoints) {
				System.out.println(endpoint);
			}
		}
		finally {
			socket.close();
		}
	}

//	private static String toString(byte[] hardwareAddress) {
//		StringBuffer sb = new StringBuffer();
//		for (byte b : hardwareAddress) {
//			sb.append(b);
//			sb.append(',');
//			
//		}
//		return sb.toString();
//	}
}
