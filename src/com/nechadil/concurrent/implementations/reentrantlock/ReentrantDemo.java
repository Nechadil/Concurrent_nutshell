package com.nechadil.concurrent.implementations.reentrantlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class which demonstrates the reentrant feature of ReentrantLock,as its name indicates :-)
 * Same feature for synchronized keyword
 * @author DachenLI
 *
 */
public class ReentrantDemo {
	
	private static Lock lock = new ReentrantLock();
	private static CountDownLatch latch = new CountDownLatch(2);
	
	private static void method1() {
		try {
			lock.lock();
			for(int i = 0; i < 10; i++) {
				System.out.println("I am method 1");
			}
		} finally {
			lock.unlock();
		}
	}
	
	private static void method2() {
		try {
			lock.lock();
			System.out.println("I am method 2");
		} finally {
			lock.unlock();
		}
	}

	private static void method2InMethod1() {
		try {
			lock.lock();
			for(int i = 0; i < 10; i++) {
				System.out.println("I am method 1");
				if(i == 2) method2();
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Two methods using the same lock.
	 * If they are in different threads, one will finish execution before the other starts
	 */
	public static void twoMethodsInDifferntThreadsTest() {
		new Thread(() -> {
			method1();
			latch.countDown();
		}).start();
		
		new Thread(() -> {
			method2();
			latch.countDown();
		}).start();
	}
	
	/**
	 * Two methods using the same lock.
	 * If they are in the same thread, since the lock is <b>reentrant</b>, they can have the lock at the same time.
	 */
	public static void twoMethodsInSammeThreadTest() {
		new Thread(() -> {
			method2InMethod1();
		}).start();
	}
	
	public static void main(String[] args) {
		System.out.println("================Two methods in two different threads=======================");
		twoMethodsInDifferntThreadsTest();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("================Two methods in the same thread=======================");
		twoMethodsInSammeThreadTest();
	}
}
