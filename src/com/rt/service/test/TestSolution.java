package com.rt.service.test;

import com.rt.service.OrderService;
import com.rt.service.impl.OrderServiceImpl;

public class TestSolution {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("sdfsdfdsf");
		OrderService oService = new OrderServiceImpl();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i < 16; i++) {
					//Thread.sleep(1000);
					System.out.println(i+" "+oService.order("XYZ", "ABC"));
				}
			}
		});
		t1.start();

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 61; i < 71; i++) {
					//Thread.sleep(1000);
					System.out.println(i+" "+oService.order("XYZ", "ABC"));
				}
			}
		});
		t2.start();
		t1.join();
		t2.join();
		oService.cleanup();
	}

}