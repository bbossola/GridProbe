package com.gridprobe.grid.core.protocol.ip;

import java.net.InterfaceAddress;
import java.nio.ByteBuffer;
import java.util.Set;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.Lan;
import com.gridprobe.grid.GridException.Failure;
import com.gridprobe.grid.core.CoreGrid;
import com.gridprobe.grid.serialization.Binaryzable;
import com.gridprobe.grid.serialization.BinaryzationException;

public class IPLan implements Lan, Binaryzable {

	private byte[] address;
	private byte cidr;
	private transient CoreGrid grid;

	// required for serialization
	public IPLan() {
	}

	public IPLan(byte[] address, byte cidr) {
		this.address = address;
		this.cidr = cidr;
	}

	public void inject(CoreGrid grid) {
		this.grid = grid;
	}

	public byte[] netmask() {
		return toNetmask(cidr, address.length);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		append(sb, address);
		sb.append('/');
		sb.append(cidr);
		sb.append(']');

		return sb.toString();
	}

	// FIXME: dumb implementation - should be improved
	@Override
	public int hashCode() {
		int tot = cidr;
		for (byte b : address) {
			int i = (int) b;
			tot *= i;
		}

		return tot;
	}

	private void append(StringBuffer sb, byte[] addr) {
		for (int i = 0; i < addr.length; i++) {
			if (i > 0)
				sb.append('.');
			int x = (int) (addr[i] & 0xff);
			sb.append(x);
		}
	}

	@Override
	public boolean equals(Object obj) {
		try {
			IPLan other = (IPLan) obj;

			if (address.length != other.address.length)
				return false;

			if (cidr != other.cidr)
				return false;

			for (int i = 0; i < address.length; i++) {
				if (address[i] != other.address[i])
					return false;
			}
		} catch (Exception ignore) {
			return false;
		}

		return true;
	}

	@Override
	public Set<? extends Agent> agents() throws GridException {
		return grid.agentRegistry().getByLan(this);
	}

	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
		cidr = buffer.get();

		int len = (int) buffer.get();
		address = new byte[len];
		buffer.get(address);
	}

	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
		buffer.put(cidr);

		buffer.put((byte) address.length);
		buffer.put(address);
	}

	public static IPLan createFrom(InterfaceAddress inetAddress) throws GridException {
		byte[] address = inetAddress.getAddress().getAddress();
		final int len = address.length;
		if (len != 4 && len != 6)
			throw new GridException(Failure.NET_ERROR, "Only IPV4/IPV6 are supported - received address: "
					+ toString(address));

		byte cidr = (byte) inetAddress.getNetworkPrefixLength();
		byte[] netmask = toNetmask(cidr, len);
		for (int i = 0; i < len; i++) {
			address[i] &= netmask[i];
		}

		return new IPLan(address, cidr);
	}

	private static byte[] toNetmask(byte cidr, int addressLength) {

		byte[] netmask = new byte[addressLength];

		int fullmask = cidr > 0 ? 0x00 - (1 << ((8 * addressLength) - cidr)) : 0xFFFFFFFF;
		for (int i = 0; i < addressLength; i++) {
			int shift = 8 * (addressLength - i - 1);
			int bytemask = fullmask;
			bytemask >>= shift;
			bytemask &= 0xff;
			netmask[i] = (byte) bytemask;
		}

		return netmask;
	}

	// FIXME: genera helper method!
	private static String toString(byte[] bytearray) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytearray) {
			sb.append(b);
			sb.append(',');

		}
		return sb.toString();
	}
}
