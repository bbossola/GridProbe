package com.gridprobe.grid;

import com.gridprobe.grid.serialization.Binaryzable;

public interface Message extends Binaryzable{

	/**
	 * The agent sending the message
	 * 
	 * @return	id of the sending agent
	 */
	public Agent.Id from()
	;
	
	/**
	 * The agent recipient of the message
	 * (may be null in case of a broadcast)
	 * 
	 * @return	id of the recipient agent
	 */
	public Agent.Id to()
	;
	
	/**
	 * The credentials associated to this 
	 * message (usually coming from the sending agent)
	 * 
	 * @return	credentials set
	 */
	public abstract Credentials credentials()
	;
}