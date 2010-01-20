package com.gridprobe.grid;

/**
 * A simple report used to get information
 * about a sent message
 * 
 * @author bossola
 */
public interface Report {

	/** possible message status (@see status) */
	enum Status {INPROGRESS, DELIVERED, FAILED}

	/**
	 * Current status of delivery of the message
	 * 
	 * @return	the agent sent to
	 */
	public Status status()
	;
	
	/**
	 * When the message was sent
	 * 
	 * @return	systemtime of sent message
	 */
	public long when()
	;
}
