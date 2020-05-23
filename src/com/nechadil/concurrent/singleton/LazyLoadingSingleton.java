package com.nechadil.concurrent.singleton;

public class LazyLoadingSingleton {
	private static LazyLoadingSingleton INSTANCE;
	
	
	// Not thread safety. Two threads can access line 10 at the same time.
	public static LazyLoadingSingleton getInstaceWithoutThreadSafty() {
		if (INSTANCE == null) {
			INSTANCE = new LazyLoadingSingleton();
		}
		return INSTANCE;
	}
	
	// This is thread safety but with heavy resource use since all the block is used
	public static synchronized LazyLoadingSingleton getInstanceSynchronized() {
		if (INSTANCE == null) {
			INSTANCE = new LazyLoadingSingleton();
		}
		return INSTANCE;
	}
	
	// This is not thread safety!!!! Same problem as the first method!
	public static synchronized LazyLoadingSingleton getInstanceWithSynchronizedBlock() {
		if (INSTANCE == null) {
			synchronized (LazyLoadingSingleton.class) {
				INSTANCE = new LazyLoadingSingleton();
			}
		}
		return INSTANCE;
	}
}
