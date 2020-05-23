package com.nechadil.concurrent.basic;

import java.util.concurrent.TimeUnit;

public class ThreadConcept extends Thread{
	@Override
	public void run() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.MICROSECONDS.sleep(1L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T1");
		}
	}
	
	public static void testStart() {
		ThreadConcept t1 = new ThreadConcept();
		//Start a new thread,two threads runs in parallel
		t1.start();
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.MICROSECONDS.sleep(1L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T2");
		}
	}
}
