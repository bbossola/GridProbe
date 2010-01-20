package com.gridprobe.grid.core.protocol.ip.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridprobe.grid.Message;
import com.gridprobe.grid.core.CoreProtocol.Listener;
import com.gridprobe.grid.serialization.ObjectSerializer;
import com.gridprobe.utils.Multicaster;

public class UDPServer {

	private static Logger logger = Logger.getLogger(UDPProtocol.class.getName());

	private MulticastSocket socket;
	private int maxPacketSize;

	private Thread thread;
	private Multicaster<Listener, Message> multicaster;

	UDPServer(MulticastSocket socket, int maxPacketSize) {
		this.socket = socket;
		this.maxPacketSize = maxPacketSize;

		this.multicaster = new Multicaster<Listener, Message>(){
			@Override
			protected void dispatch(Listener listener, Message message) {
				listener.onMessage(message);
			}};
	}

	public synchronized void start() {

		if (thread != null) 
			throw new RuntimeException("UDPServer started two times? WTF?");
			
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				loop();
			}});
		thread.setDaemon(true);
		thread.start();
	}

	public synchronized void stop() {

		if (thread != null)
			throw new RuntimeException("UDPServer stopped two times? WTF?");
		
		thread.interrupt();
		thread=null;
	}

	private void loop() {
		
		byte[] buf = new byte[maxPacketSize];
		
		logger.info("Listening loop started on port "+socket.getLocalPort());
		while(!Thread.interrupted()) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				logger.log(Level.FINEST, "IOException receiving UDP packet", e);
			}

			if (Thread.interrupted())
				break;
			
			ByteBuffer buffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
			Message message = (Message)ObjectSerializer.fromBytes(buffer);
			logger.log(Level.FINEST, "Received message "+message);

			sendToListeners(message);
		}

		logger.info("Listening loop ended!");
	}

	private void sendToListeners(Message message) {
		multicaster.dispatch(message);
	}

	public void addListener(final Listener listener) {
		multicaster.addListener(listener);
	}
}
