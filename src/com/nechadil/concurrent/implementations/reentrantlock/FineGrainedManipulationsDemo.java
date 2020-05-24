package com.nechadil.concurrent.implementations.reentrantlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class demonstrates some fine-grained manipulations(compares to <b>synchronized</b> keyword) in the class {@link java.util.concurrent.locks.ReentrantLock}
 * 1. tryLock
 * 2. lockInterruptibly
 * 3. fair lock
 * @author DachenLI
 *
 */
public class FineGrainedManipulationsDemo {
	private static Lock lock = new ReentrantLock();
	private static Lock fairLock = new ReentrantLock(true);
	private static CountDownLatch latch = new CountDownLatch(2);
	
	public static void testTryLock() {
		// This thread will occupy the lock for several seconds
		new Thread(() ->  {
			try {
				lock.lock();
				System.out.println("Thread 1 === I'm taking the lock and i'll go sleep");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
				latch.countDown();
			}
		}).start();
		
		new Thread(() ->  {
			// Trylock returns false since the lock is occupied by another program
			System.out.println("Thread 2 === Status of whether i get the lock: " + lock.tryLock());
			latch.countDown();
		}).start();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Test terminated");
	}
	
	public static void testInterruptibly() {
		Thread threadTakingTheLock =  new Thread(() -> {
			try {
				lock.lock();
				System.out.println("Thread1 === I'll take the lock for a long time");
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		});
		
		Thread threadToBeInterrupted = new Thread(() -> {
			try {
				lock.lockInterruptibly();
				System.out.println("You are not supposed to see this message");
			} catch (InterruptedException e) {
				System.out.println("Thread2 === This thread is interrupted and the progress ends");
			}
		});
		threadTakingTheLock.start();
		threadToBeInterrupted.start();
		try {
			TimeUnit.SECONDS.sleep(2);
			threadToBeInterrupted.interrupt();
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread ends");
	}
	
	private static void testFair(Lock lock) {
			new Thread(() ->{
				for(int i = 0; i < 10; i++) {
					try {
						lock.lock();
						TimeUnit.MILLISECONDS.sleep(100);
						System.out.println("I'm the " + i + "th execution in the first thread");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}).start();
		
			new Thread(() ->{
				for(int i = 0; i < 10; i++) {
					try {
						lock.lock();
						System.out.println("I'm the " + i + "th execution in the second thread");
					} finally {
						lock.unlock();
					}
				} 
			}).start();
	}
	
	public static void testFairness() {
		System.out.println("Second thread execution n is coming befor first thread execution n-1");
		System.out.println("===== Unfair lock: first waiting thread is not executed before the coming one =====");
		testFair(lock);
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("===== Fair lock: first waiting thread is executed before the coming one  =====");
		testFair(fairLock);
	}
	
	public static void main(String[] args) {
//		testTryLock();
//		testInterruptibly();
//		testFairness();
	}
}
