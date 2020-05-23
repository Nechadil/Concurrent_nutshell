package com.nechadil.concurrent.singleton;

public class EagerlyLoadingSingleton {
	// JVM ensures static field has only one instance
	private static final EagerlyLoadingSingleton INSTANCE = new EagerlyLoadingSingleton();
	
	private EagerlyLoadingSingleton() {
		System.out.println("Singleton is created");
	}
	
	public static EagerlyLoadingSingleton getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) {
		EagerlyLoadingSingleton.getInstance();
	}
}
