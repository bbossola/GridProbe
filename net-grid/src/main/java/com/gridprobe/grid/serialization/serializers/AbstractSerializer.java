package com.gridprobe.grid.serialization.serializers;

import java.nio.ByteBuffer;

import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.Serializer;

public abstract class AbstractSerializer<T> implements Serializer<T> {

	@Override
	public final void toBytes(ByteBuffer buffer, T object) throws BinaryzationException {
		try {
			byte[] bytes = toBytes(object);
			buffer.put((byte) bytes.length);
			buffer.put(bytes);
		} catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}

	@Override
	public final T fromBytes(ByteBuffer buffer) throws BinaryzationException {
		try {
			final int size = (int) buffer.get();
			final byte[] bytes = new byte[size];
			buffer.get(bytes);
			return fromBytes(bytes);
		} catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}

	protected abstract byte[] toBytes(T object) throws Exception;

	protected abstract T fromBytes(byte[] bytearrayFromBytes) throws Exception;
}
