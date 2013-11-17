package com.gridprobe.grid.core;

import java.util.Collections;
import java.util.HashSet;

import junit.framework.TestCase;

import com.gridprobe.grid.Endpoint;
import com.gridprobe.grid.core.CoreAgent.Id;

public class CoreAgentTest extends TestCase {
    
    public void testHashCode() {
        CoreAgent original = new CoreAgent(new Id(123), "one", Collections.<Endpoint>emptySet());
        CoreAgent copy = new CoreAgent(new Id(123), "one", Collections.<Endpoint>emptySet());
        
        HashSet<CoreAgent> agents = new HashSet<CoreAgent>();
        agents.add(original);
        agents.add(copy);
        
        assertEquals(1, agents.size());
    }

}
