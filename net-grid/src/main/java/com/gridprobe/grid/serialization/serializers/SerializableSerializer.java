package com.gridprobe.grid.serialization.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableSerializer extends AbstractSerializer<Serializable> {

	protected Serializable fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Serializable obj = (Serializable)ois.readObject();
		ois.close();
		
		return obj;
	}

	protected  byte[] toBytes(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.close();
		
		return baos.toByteArray();
	}

}
