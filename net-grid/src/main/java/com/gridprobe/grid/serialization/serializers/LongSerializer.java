package com.gridprobe.grid.serialization.serializers;


public class LongSerializer extends AbstractSerializer<Long> {

	protected byte[] toBytes(Long number) {

		long value = number.longValue();
		byte[] bytes = new byte[8];
		for (int i = 0; i < bytes.length; i++) {
			int offset = (bytes.length - 1 - i) * 8;
			bytes[i] = (byte) ((value >> offset) & 0xff);
		}

		return bytes;
	}

	protected Long fromBytes(byte[] bytes) {

		long value = 0l;
		int i = 0;
		for (int offset = 8; offset >= 0; offset--) {
			value |= ((long) bytes[i++] & 0xff) << (offset * 8);
		}
		
		return Long.valueOf(value);
	}
}
