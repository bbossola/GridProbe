package com.gridprobe.grid;

// TODO - make it runtimeexception as soon as interfaces are defined
public class GridException extends Exception {

	private static final long serialVersionUID = 1L;

	public static enum Failure {
	    IO_ERROR, 
	    NO_ROUTE,
	    NET_ERROR, 
	    SIZE_OVERFLOW
	};

	private Failure failure;
	
	public GridException(Failure failure, Throwable ex) {
		super(ex);
		this.failure = failure;
	}

	public GridException(Failure failure, Throwable ex, String message) {
		super(message, ex);
		this.failure = failure;
	}

	public GridException(Failure failure, String message) {
		super(message);
		this.failure = failure;
	}

	public Failure failure() {
		return failure;
	}
}
