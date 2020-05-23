package com.nechadil.concurrent.basic;

/**
 * Use synchronized block with a lock to keep visited data's consistency
 * @author DachenLI
 *
 */
public class SynchronizedConcept {
	private static Integer counter;
	private static Object lock = new Object();

	// Return thread which increment 10 times the counter;
	public static Thread generateIncrementationThread() {
		return new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				counter++;
			}
		});
	}

	public static Thread generateIncrementationThreadWithSync() {
		return new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				synchronized (lock) {
					counter++;
				}
			}
		});
	}

	public static int withoutLock() {
		counter = 0;
		Thread t1 = generateIncrementationThread();
		Thread t2 = generateIncrementationThread();
		try {
			t1.start();
			t2.start();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return counter;
	}

	public static int withLock() {
		counter = 0;
		Thread t1 = generateIncrementationThreadWithSync();
		Thread t2 = generateIncrementationThreadWithSync();
		try {
			t1.start();
			t2.start();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return counter;
	}

	public static void main(String[] args) {
		// Result is not always 20000
		for (int i = 0; i < 5; i++) {
			System.out.println("Without lock result:" + withoutLock());
		}
		// Result is 20000
		for (int i = 0; i < 5; i++) {
			System.out.println("With lock result:" + withLock());
		}
	}
}
