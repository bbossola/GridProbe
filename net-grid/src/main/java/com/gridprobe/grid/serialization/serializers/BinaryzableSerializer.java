package com.gridprobe.grid.serialization.serializers;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;

import com.gridprobe.grid.serialization.Binaryzable;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.Serializer;

public class BinaryzableSerializer implements Serializer<Binaryzable> {

	private static final Object[] NOARGS_VALUES = new Object[] {};
	private static Class<?>[] NOARGS_CLASSES = new Class<?>[] {};

	private StringSerializer strings = new StringSerializer();

	@Override
	public void toBytes(ByteBuffer buffer, Binaryzable obj) throws BinaryzationException {
		buffer.put(obj == null ? (byte) 0 : (byte) 1);
		if (obj != null) {
			strings.toBytes(buffer, obj.getClass().getName());
			obj.toBytes(buffer);
		}
	}

	@Override
	public Binaryzable fromBytes(ByteBuffer buffer) throws BinaryzationException {
		try {
			boolean notNull = (buffer.get() != 0);
		
			Binaryzable o = null;
			if (notNull) {
				String className = strings.fromBytes(buffer);
				Constructor<?> ctor = getNoargConstructor(className);
				o = (Binaryzable) ctor.newInstance(NOARGS_VALUES);
				o.fromBytes(buffer);
			}
		
			return o;
		} catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}

	private static Constructor<?> getNoargConstructor(String className) throws NoSuchMethodException,
			ClassNotFoundException {
		Constructor<?> ctor = Class.forName(className).getDeclaredConstructor(NOARGS_CLASSES);
		ctor.setAccessible(true);
		return ctor;
	}

}
