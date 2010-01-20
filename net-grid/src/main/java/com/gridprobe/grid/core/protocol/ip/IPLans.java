package com.gridprobe.grid.core.protocol.ip;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.gridprobe.grid.GridException;
import com.gridprobe.grid.GridException.Failure;

// FIXME: dunno how to call this
public class IPLans {

	public IPLans() {}
	
	public Set<IPLan> all() throws GridException {
		Set<IPLan> lans = new HashSet<IPLan>();
		collect(lans);
		return lans;
	}
	
	public Set<IPLan> on(NetworkInterface netIf) throws GridException {
		Set<IPLan> lans = new HashSet<IPLan>();
		collect(lans, netIf);
		return lans;
	}
	
	private void collect(Set<IPLan> lans) throws GridException {
        Enumeration<NetworkInterface> nets = getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            collect(lans, netIf);
        }
	}

	private Enumeration<NetworkInterface> getNetworkInterfaces() throws GridException  {
		try {
			return NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			throw new GridException(Failure.NET_ERROR, e);
		}
	}

	private void collect(Set<IPLan> lans, NetworkInterface netIf) throws GridException {
	    for (InterfaceAddress address : netIf.getInterfaceAddresses()) {
	        if (!address.getAddress().isLoopbackAddress())
	        	lans.add(IPLan.createFrom(address));
	    }

		Enumeration<NetworkInterface> subIfs = netIf.getSubInterfaces();
        for (NetworkInterface subIf : Collections.list(subIfs)) {
        	collect(lans, subIf);
        }
	}
	
	public static void main(String args[]) throws Exception {
		Set<IPLan> lans = new IPLans().all();
		for (IPLan lan : lans) {
			System.out.println(lan);
		}
	}
}
