package com.gridprobe.grid.serialization;

import java.nio.ByteBuffer;

public interface Serializer<T extends Object> {

	public void toBytes(ByteBuffer buffer, T obj) throws BinaryzationException 
	;

	public T fromBytes(ByteBuffer buffer) throws BinaryzationException 
	;

}
