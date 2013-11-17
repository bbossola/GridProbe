package com.gridprobe.grid.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.Lan;

public class AgentRegistry {

	private static Logger logger = Logger.getLogger(AgentRegistry.class.getName());

	private Set<CoreAgent> agents;

	AgentRegistry() {
		this.agents = new HashSet<CoreAgent>();
	}
	
	Set<CoreAgent> list() {
		return Collections.unmodifiableSet(agents);
	}

	public void register(Agent source) {
		CoreAgent agent = toCore(source);
		if (logger.isLoggable(Level.FINEST)) logger.finest("Agent register request received - agent: "+agent+ "(hashcode: "+agent.hashCode()+")");
		agents.add(agent);
	}

	public void unregister(Agent source) {
		CoreAgent agent = toCore(source);
        if (logger.isLoggable(Level.FINEST)) logger.finest("Agent unregister request received - agent: "+agent+ "(hashcode: "+agent.hashCode()+")");
		agents.remove(agent);
	}

	private CoreAgent toCore(Agent source) {
		if (!(source instanceof CoreAgent)) {
			final String msg = "Invalid agent register request received - agent: "+source;
			logger.warning(msg);
			throw new IllegalArgumentException(msg);
		}

		return (CoreAgent)source;
	}

	public Set<CoreAgent> getByLan(Lan lan) {
		Set<CoreAgent> result = new HashSet<CoreAgent>();
		for (CoreAgent agent : agents) {
			if (agent.isOn(lan))
				result.add(agent);
		}

		return result;
	}

	public Set<? extends Lan> lans()  {
		
		Set<Lan> result = new HashSet<Lan>();
		for (CoreAgent agent : agents) {
			for(Endpoint ep: agent.endpoints())
				result.add(ep.lan());
		}

		return result;
	}

}
