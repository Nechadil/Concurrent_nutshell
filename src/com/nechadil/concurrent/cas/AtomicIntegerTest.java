package com.nechadil.concurrent.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AtomicIntegerTest {
	private static Integer count = 0;
	private static AtomicInteger atomicCount = new AtomicInteger();
	
	private static List<Thread> threads;
	
	public static void testCount(Consumer<String> consumer) {
		threads = new ArrayList<Thread>();
		
		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(()-> {
				for (int j = 0; j < 1000; j++) {
					consumer.accept("abc");
				}
			}));
		}
		threads.forEach(thread -> thread.start());
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void main(String[] args) {
		testCount(test -> {
			count++;
		});
		
		testCount(test -> {
			atomicCount.incrementAndGet();
		});
		
		System.out.println("Count value is:" + count);
		System.out.println("AtomicCount value is:" + atomicCount);
	}
}
