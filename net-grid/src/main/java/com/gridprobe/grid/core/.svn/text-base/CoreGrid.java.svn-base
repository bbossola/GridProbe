package com.gridprobe.grid.core;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridprobe.grid.Agent;
import com.gridprobe.grid.Grid;
import com.gridprobe.grid.GridException;
import com.gridprobe.grid.Lan;
import com.gridprobe.grid.Message;
import com.gridprobe.grid.Protocol;
import com.gridprobe.grid.GridException.Failure;
import com.gridprobe.grid.core.CoreProtocol.Listener;
import com.gridprobe.grid.core.actions.DiscoveryAction;
import com.gridprobe.grid.core.actions.JoiningAction;
import com.gridprobe.grid.core.actions.LeavingAction;

public class CoreGrid implements Grid {

	private static Logger logger = Logger.getLogger(CoreGrid.class.getName());
	private static CoreGrid instance = new CoreGrid();

	public static CoreGrid instance() {
		return instance ;
	}

	private CoreAgent self = null;
    private AgentRegistry agentRegistry;
	private MessageDispatcher messageDispatcher;
    private CoreTransport transport;
    
	private Listener protocolListener;


    private CoreGrid() {
    	transport = new CoreTransport();
		agentRegistry = new AgentRegistry();
		messageDispatcher = new MessageDispatcher();
		
		createProtocolListener();
		setShutdownHook();
		
		logger.finer("Grid is ready");
	}

	private void createProtocolListener() {
		protocolListener = new Listener() {
			@Override
			public void onMessage(Message message) {
				dispatchAction(message);
			}
		};
	}

	@Override
	public Agent join(String nickname) throws GridException {
		if (self != null)
			throw new GridException(Failure.NET_ERROR,"Grid already joined");

		self = new CoreAgent(nickname, transport.endpoints());
		broadcast(new JoiningAction(credentials()));
		logger.finer("Network joined");
		return self;
	}

	@Override
	public void leave() throws GridException {
		verifyInGrid();
		
		broadcast(new LeavingAction(credentials()));
		self = null;
		logger.finer("Network left");
	}

	@Override
	public Set<? extends Agent> agents() throws GridException {
		verifyInGrid();
		return agentRegistry.list();
	}

	@Override
	public Set<? extends Lan> lans() throws GridException {
		verifyInGrid();
		return agentRegistry.lans();
	}

	@Override
	public Agent whoami() throws GridException {
		return self;
	}

	@Override
	public void install(Protocol protocol) throws GridException {
		final CoreProtocol proto = transport.install(protocol);
		proto.addListener(protocolListener);
		messageDispatcher.startListening(proto);
	}

	@Override
	public Set<? extends Protocol> list() {
		return transport.list();
	}
	
	@Override
	public void addListener(GridListener listener) {
		messageDispatcher.addGridListener(listener);
	}

	@Override
	public void removeListener(GridListener listener) {
		messageDispatcher.removeGridListener(listener);
	}

	@Override
	public void broadcast(final Message message) throws GridException {
		verifyInGrid();
		transport.broadcast(message);
	}
	
	@Override
	public void discover() throws GridException {
		verifyInGrid();
		broadcast(new DiscoveryAction(credentials()));
	}

	public AgentRegistry agentRegistry() {
		return agentRegistry;
	}

	public CoreCredentials credentials() throws GridException {
		return new CoreCredentials(this);
	}

	private void dispatchAction(Message message) {
		if (message instanceof CoreAction) {
			final CoreAction action = (CoreAction)message;
			try {
				action.execute(CoreGrid.this);
			} catch (GridException e) {
				logger.log(Level.WARNING, "Unable to execute action "+action, e);
			}
		}
	}
	
	private void setShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				try {
					logger.log(Level.FINEST, "Executing shutdown hook (leave action)");
					leave();
				} catch (GridException e) {
					logger.log(Level.FINEST, "Problem executing shutdown hook leave action ", e);
				}
			}
		});
	}

	private void verifyInGrid() throws GridException {
		if (self == null)
			throw new GridException(Failure.NET_ERROR,"You're not inside the grid");
	}
}
