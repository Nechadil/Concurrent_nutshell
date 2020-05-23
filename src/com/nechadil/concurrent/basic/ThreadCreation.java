package com.nechadil.concurrent.basic;


/**
 *   Three methods to create a thread
 *   1. extends Thread
 *   2. pass a Runnable implementation to a new Thread 
 *   3. from a thread pool
 * @author DachenLI
 *
 */
public class ThreadCreation {
	private static class CreateByThread extends Thread {
		@Override
		public void run() {
			System.out.println("Thread created by extending thread");
		}
	}
	
	private static class CreateByRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("Thread created by implementing Runnable");
		}
	}
	
	public static void main(String[] args) {
		CreateByThread createByThread = new CreateByThread();
		Thread createdByRunnable = new Thread(new CreateByRunnable());
		Thread createdByLambda = new Thread(() -> {
			System.out.println("Thread created by lambda expression");});
		createByThread.start();
		createdByRunnable.start();
		createdByLambda.start();
	}
}
