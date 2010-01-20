package com.gridprobe.grid.core;

import java.util.logging.Logger;

import com.gridprobe.grid.Message;
import com.gridprobe.grid.Grid.GridListener;
import com.gridprobe.grid.core.CoreProtocol.Listener;
import com.gridprobe.utils.Multicaster;

public class MessageDispatcher  {

	private static Logger logger = Logger.getLogger(MessageDispatcher.class.getName());

    private Listener protocolListener;
    private Multicaster<GridListener,Message> multicaster;
	
	MessageDispatcher() {
		this.protocolListener = new Listener() {
			@Override
			public void onMessage(Message message) {
				dispatch(message);
			}
		};
		
		this.multicaster = new Multicaster<GridListener, Message>() {
			@Override
			protected void dispatch(GridListener listener, Message message) {
				listener.onMessage(message);
			}};
			
	}
	
	void startListening(CoreProtocol protocol) {
		protocol.addListener(protocolListener);
	}
	
	void dispatch(Message message) {
		logger.finest("Dispatching message "+message);
		multicaster.dispatch(message);
	}

	void addGridListener(GridListener listener) {
		multicaster.addListener(listener);
	}

	void removeGridListener(GridListener listener) {
		multicaster.removeListener(listener);
	}
}
