package com.gridprobe.grid.serialization;

import java.nio.ByteBuffer;

import com.gridprobe.grid.serialization.samples.Sample;
import com.gridprobe.grid.serialization.samples.SamplePackage;
import com.gridprobe.grid.serialization.samples.SamplePrivate;
import com.gridprobe.grid.serialization.samples.SampleProtected;

import junit.framework.TestCase;
 
public class ObjectSerializationWithNonPublicConstructorsTest extends TestCase {
	
	public void testPublic() {
		
		checkSerialization(new Sample("hello"));
	}

	public void testProtected() {
		
		checkSerialization(new SampleProtected("hello"));
	}

	public void testPackage() {
		
		checkSerialization(new SamplePackage("hello"));
	}

	public void testPrivate() {
		
		checkSerialization(new SamplePrivate("hello"));
	}

	private void checkSerialization(Sample sample) {
		Sample result = serialize(sample);
		
		assertEquals(sample.getClass(), result.getClass());
		assertEquals(sample.text(), result.text());
	}

	private Sample serialize(Sample sample) {
		ByteBuffer buffer = ByteBuffer.allocate(8192);

		ObjectSerializer.toBytes(buffer, sample);
		buffer.flip();
		Sample result = (Sample) ObjectSerializer.fromBytes(buffer);
		return result;
	}
}
