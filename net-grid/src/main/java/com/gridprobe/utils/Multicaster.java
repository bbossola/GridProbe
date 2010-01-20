package com.gridprobe.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * This class is an asynchronous multicaster, allows you to send asynchronous
 * notifications to an arbitrary number of listeners, manged by this
 * It's thread safe, it uses internally an Executor to deliver notifications,
 * but you can provide your own instance.
 * 
 * To use it, you need to extend this class and provide an implementation of the
 * dispatch(L listener, M message) method - it should be straightforward.
 * 
 * @author bossola
 *
 * @param <L>	The listener class
 * @param <M>	The message class (the argument to notify)
 */
public abstract class Multicaster<L,M> {
	
	private Set<L> listeners = new HashSet<L>();
	private Executor executor;

	public Multicaster() {
		this(ExecutorServices.newFixedDaemonThreadPool(numberOfThreads()));
	}

	public Multicaster(Executor executor) {
		this.executor = executor;
	}

	public void addListener(L listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeListener(L listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void dispatch(final M message) {
		synchronized (listeners) {
			for (final L listener : listeners) {
				executor.execute(new Runnable(){
					@Override
					public void run() {
						dispatch(listener,message);
					}});
			}
		}
	}

	protected abstract void dispatch(L listener, M message)
	;
	
	private static int numberOfThreads() {
		return SystemProperties.getInt(Multicaster.class, "default_thread_num", 3);
	}
}
