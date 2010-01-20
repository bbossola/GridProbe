package com.gridprobe.grid.serialization;

import java.nio.ByteBuffer;

import junit.framework.TestCase;
 
public abstract class AbstractSerializationTest extends TestCase {
	

	@SuppressWarnings("unchecked")
	protected <T extends Binaryzable> T serialize(T sample) {
		ByteBuffer buffer = ByteBuffer.allocate(8192);

		ObjectSerializer.toBytes(buffer, sample);
		buffer.flip();
		return (T) ObjectSerializer.fromBytes(buffer);
	}
}
