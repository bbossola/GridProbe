package com.gridprobe.grid.serialization;

public class BinaryzationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private BinaryzationException(Throwable ex) {
		super(ex);
	}

	private BinaryzationException(String reason) {
		super(reason);
	}

	public static BinaryzationException create(Exception ex) {
		if (ex instanceof BinaryzationException)
			return (BinaryzationException) ex;
		else
			return new BinaryzationException(ex);
	}

	public static BinaryzationException create(String reason) {
		return new BinaryzationException(reason);
	}
}

