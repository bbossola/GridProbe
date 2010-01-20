package com.gridprobe.grid;

import java.util.Set;

import com.gridprobe.grid.serialization.Binaryzable;


public interface Agent extends Binaryzable {

	/**
	 * The unique ID of this agent
	 */
	public Id id() 
	;
	
	/**
	 * Send a message to this agent. Note that this action is asynchronous
	 * meaning that when this call is over there's no guarantee that
	 * this message is delivered - please look at the report
	 * 
	 * @param message	the message I want to send
	 * @return	the report containing info about the delivery of the message
	 * @throws GridException
	 */
	public Report send(Message message) throws GridException
	;
	
	/**
	 * Send a message to this agent. Note that this action is asynchronous
	 * meaning that when this call is over there's no guarantee that
	 * this message is delivered - please look at the report
	 * 
	 * @param message	the message I want to send
	 * @param callback	a callback where I can receive info about th status of the delivery
	 * @return	the report containing info about the delivery of the message
	 * @throws GridException
	 */
	public Report send(Message message, MessageCallback callback) throws GridException
	;
	
	/**
	 * Return the list of endpoints available for this agent
	 * 
	 * @return	list of endpoints
	 */
	public Set<Endpoint> endpoints() 
	;
	
	/**
	 * The callback interface triggered
	 * when a message is sent
	 * 
	 * @author bossola
	 */
	public static interface MessageCallback {
		
		public void onSuccess (Report report)
		;
		
		public void onFailure (Report report)
		;
	}

	/**
	 * A simple class to map the agent id
	 * @author bossola
	 */
	public static interface Id extends Binaryzable {

		public Long value() 
		;
	}

}
