package com.gridprobe.grid.serialization.serializers;

import java.io.UnsupportedEncodingException;

public class StringSerializer extends AbstractSerializer<String> {

	private static final String ENCODING = "UTF-8";

	@Override
	protected String fromBytes(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, ENCODING);
	}

	@Override
	protected byte[] toBytes(String object) throws UnsupportedEncodingException {
		return object.getBytes(ENCODING);
	}
}
