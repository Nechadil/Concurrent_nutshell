package com.nechadil.concurrent.implementations;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("All threads are prepared.Ready to process"));
		for(int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					System.out.println("Thread is preparing");
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
