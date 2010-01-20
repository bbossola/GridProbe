package com.gridprobe.grid;

import java.util.Set;


/**
 * This interface maps a group of agents, and allows your agent to join and leave the grid
 * 
 * @author bossola
 * 
 * @see Agent
 */
public interface Grid {

	/**
	 * A generic listener that will receive all the broadcast messages 
	 * and all the messages routed to this agent
	 * 
	 * @author bossola
	 *
	 */
	public interface GridListener {
		public void onMessage(Message message)
		;
	}

	/**
	 * Returns the set of agents detected in the grid. Note that this method
	 * will not do a synchronous discovery, it will return the set of the agents 
	 * already discovered.
	 * 
	 * @return set of agents already discovered
	 * @throws GridException
	 */
	public Set<? extends Agent> agents() throws GridException
	;
	
	/**
	 * Allows this agent to join the grid. The Agent object representing this
	 * agent is returned
	 * 
	 * @param nickname an optional nickname to be included as a part of the signature
	 * @return the instance of Agent representing your agent
	 * @throws GridException
	 */
	public Agent join(String nickname) throws GridException
	;

	/**
	 * Returns the Agent object representing this agent. 
	 * 
	 * @return the instance of Agent representing your agent, null if not joined yet
	 * @throws GridException
	 */
	public Agent whoami() throws GridException
	;

	/**
	 * Allows this agent to leave the grid. 
	 * 
	 * @throws GridException
	 */
	public void leave() throws GridException
	;

	/**
	 * Ask the grid to execute an immediate discovery. This will be anyway
	 * execute asynchronously, and due to the nature of the grid you can't 
	 * know when the discovery will expire. Sorry :)
	 * 
	 * @throws GridException
	 */
	public void discover() throws GridException
	;

	/**
	 * Returns the set of the Lan currently represented in this grid, so the number
	 * of Lan where at least one Agent is present
	 * 
	 * Please note that "Lan" is not directly translatable into LAN (in TCP terms)
	 * as the protocols used by the grid may use other means (i.e. Bluetooth)
	 * 
	 * @return set of Lan currently represente in this grid
	 * @throws GridException
	 */
	public Set<? extends Lan> lans() throws GridException
	;
	
	/**
	 * Ask the grid to send a broadcast message to all the agents.
	 * Note that this is a best-effort mechanism, so there're no guarantees of delivery: 
	 * this should be handled at a higher level
	 * 
	 * @throws GridException
	 */
	public void broadcast(final Message message) throws GridException
	;

	/**
	 * Adds a generic listener that will receive all the broadcast messages 
	 * and all the messages routed to this agent
	 * 
	 * @param listener	the listener to be added
	 */
	public void addListener(GridListener listener)
	;
	
	/**
	 * Remove a generic listener added by addListener
	 * 
	 * @param listener	the previously addded listener to be removed
	 */
	public void removeListener(GridListener listener)
	;
	
	/**
	 * Allows you to install a new protocol into this grid. 
	 * 
	 * FIXME - do we really need this method public here?
	 * @param protocol	the protocol to be installed
	 * @throws GridException
	 */
	public void install(Protocol protocol) throws GridException
	;

	/** 
	 * Returns the list of the currently installed protocols
	 * 
	 * FIXME - do we really need this method public here?
	 * @return set of installed protocols
	 */
	public Set<? extends Protocol> list() 
	;
	
}
