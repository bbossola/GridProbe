package com.gridprobe.grid.util;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UUID {

	private static Logger logger = Logger.getLogger(UUID.class.getName());
	private static final String[] PREFERRED_ITFS = {"eth0", "eth1", "wlan0"};
	
	public static long generateLong()  {
		byte[] mac = getMacAddressByPreferred();
		if (mac == null)
			mac = getFirstMacAddress();
		
		if (mac == null)
			throw new RuntimeException("Unable to find a suitable MAC address to generate agent unique ID");

		byte[] addr = createUniqueAddress(mac);
		return toLong(addr);
	}

	// FIXME
	// this is simply adding two random bytes to the end, not exactly rocket science :( 
	// Can we find a better way to uniquely identify an agent on a host?
	private static byte[] createUniqueAddress(byte[] mac) {

		byte addr[] = new byte[8];
		for (int i = 0; i < addr.length; i++) {

			byte val;
			if (i < mac.length)
				val = mac[i];
			else
				val = (byte)((Math.random()*255));

			addr[i] = val;
		}

		return addr;
	}

	private static byte[] getFirstMacAddress() {
		
		byte[] mac = null;
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();

			if (nics != null) 
				while (nics.hasMoreElements()) {
					mac = nics.nextElement().getHardwareAddress();
				}
		}
		catch (Exception ex) {
			logger.log(Level.FINEST, "Error while detecting MAC address", ex);
		}
		
		return mac;
	}

	private static byte[] getMacAddressByPreferred() {
		byte[] mac = null;

		int i=0;
		while(mac == null)
			mac = getMacAddress(PREFERRED_ITFS[i++]);
		
		return mac;
	}
	

	private static byte[] getMacAddress(String name) {
		byte[] mac = null;
	
		try {
			mac = NetworkInterface.getByName(name).getHardwareAddress(); 
		}
		catch (Exception ex) {
			logger.log(Level.FINEST, "Error while detecting MAC address", ex);
		}
		
		return mac;
	}

	private static long toLong(byte[] mac) {
		if (mac == null)
			return 0;
		
		long result=0;
		for (int i = 0; i < mac.length; i++) {
			long val = (long)(mac[i]&0xff);
			result |= val<<(8*i);
		}
		
		return result;
	}
	
	public static void main(String args[]) {
		System.out.println(Long.toHexString(UUID.generateLong()));
	}
}
