/**
 * 
 */
package com.gridprobe.grid.serialization.samples;

import java.nio.ByteBuffer;

import com.gridprobe.grid.serialization.Binaryzable;
import com.gridprobe.grid.serialization.BinaryzationException;
import com.gridprobe.grid.serialization.ObjectSerializer;
import com.gridprobe.grid.serialization.Serializer;

public class Sample implements Binaryzable {

	private String txt;
	private Long lval = 0L;
	
	public Sample(){
	}
	
	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Long getLval() {
		return lval;
	}

	public void setLval(Long lval) {
		this.lval = lval;
	}

	public Sample(String text){
		this.txt = text;
	}

	public String text() {
		return txt;
	}
	
	@Override
	public void fromBytes(ByteBuffer buffer) throws BinaryzationException {
        Serializer<String> serializer = ObjectSerializer.forClass(String.class);
		txt =  serializer.fromBytes(buffer);
		lval = buffer.getLong();
	}
 
	@Override
	public void toBytes(ByteBuffer buffer) throws BinaryzationException {
        Serializer<String> serializer = ObjectSerializer.forClass(String.class);
        serializer.toBytes(buffer, txt);
		buffer.putLong(lval);
	}
}