package com.nechadil.concurrent.singleton;

public class LazyLoadingSingletonImproved {
	
	/**
	 * Volatile avoids CPU instruction reordering.
	 * In line 16, if the reference to INSTANCE instruction is reordered before initiate INSTANCE
	 * a initiated (but with default values!!!) instance will be returned 
	 */
	private static volatile LazyLoadingSingletonImproved INSTANCE;
	
	public static LazyLoadingSingletonImproved getInstanceWithDoubleCheckLocking() {
		// First test to avoid using lock
		if(INSTANCE == null) {
			synchronized (LazyLoadingSingleton.class) {
				// This avoids two threads pass the first test and try to create two times the instance
				if(INSTANCE == null) {
					INSTANCE = new LazyLoadingSingletonImproved();
				}
			}
		}
		return INSTANCE;
	}
}
