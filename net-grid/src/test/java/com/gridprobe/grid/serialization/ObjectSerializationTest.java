package com.gridprobe.grid.serialization;

public class ObjectSerializationTest extends AbstractSerializationTest{

	public void testSerializeNull() {
		assertNull(serialize(null));
	}
}
