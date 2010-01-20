package com.gridprobe.grid.serialization;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.gridprobe.grid.serialization.serializers.BinaryzableSerializer;
import com.gridprobe.grid.serialization.serializers.LongSerializer;
import com.gridprobe.grid.serialization.serializers.SerializableSerializer;
import com.gridprobe.grid.serialization.serializers.StringSerializer;

@SuppressWarnings("unchecked")
public class ObjectSerializer {

	private static final byte MARKER_BINARYZABLE = 0x01;
	private static final byte MARKER_SERIALIZABLE = 0x02;

	private static SerializableSerializer serializables = new SerializableSerializer();
	private static BinaryzableSerializer binaryzables = new BinaryzableSerializer();

	private static Map<Class<?>, Serializer> serializers = new HashMap<Class<?>, Serializer>();
	static {
		serializers.put(Serializable.class, serializables);
		serializers.put(Binaryzable.class, binaryzables);
		serializers.put(String.class, new StringSerializer());
		serializers.put(Long.class, new LongSerializer());
		// FIXME: add more custom serializers please!!!
	}

	private ObjectSerializer() {
	}

	public static Serializer<S extends Class> forClass(S c) {
		Serializer serializer = serializers.get(c);
		if (serializer == null) {
			if (c.isAssignableFrom(Serializable.class))
				serializer = serializables;
			else if (c.isAssignableFrom(Binaryzable.class))
				serializer = binaryzables;
		}

		if (serializer == null)
			throw BinaryzationException.create("Unable to find a suitable serializer for class "+c+" - at least please implement java.io.Serializable!");

		return serializer;
	}
	
	
	public static void toBytes(ByteBuffer buffer, Object o) {

		try {
			if (o == null || o instanceof Binaryzable) {
				buffer.put(MARKER_BINARYZABLE);
				binaryzables.toBytes(buffer, (Binaryzable) o);
			} else {
				buffer.put(MARKER_SERIALIZABLE);
				serializables.toBytes(buffer, (Serializable) o);
			}
		} catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}

	public static Object fromBytes(ByteBuffer buffer) {

		try {
			byte marker = buffer.get();
			if (marker == MARKER_BINARYZABLE)
				return binaryzables.fromBytes(buffer);
			else if (marker == MARKER_SERIALIZABLE)
				return serializables.fromBytes(buffer);
			else
				throw new IllegalArgumentException("Illegal serialization marker: " + marker);
		} catch (Exception any) {
			throw BinaryzationException.create(any);
		}
	}
}
