package com.rt.service.impl;

import com.rt.service.OrderService;
import com.rt.service.impl.util.RateLimiterUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OrderServiceImpl implements OrderService {
	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	@Override
	// GET service - return order details
	// Client(s) calls order() API multiple times, we want to limit these calls depends on the license type
	public String order(String clientToken, String orderId) {
		//Validate incoming request
		String response = null;
		try {
			Future<String> future = executor.submit(new OrderTask(clientToken, orderId));
			response = future.get(20, TimeUnit.SECONDS);// Request processing times out after 20 seconds
		} catch (InterruptedException e) {
			response = e.getMessage();
		} catch (ExecutionException e) {
			response = e.getCause().getMessage();
		} catch (TimeoutException e) {
			response = "Order service timeout error";
		}
		return response;
	}

	public void cleanup() {
		executor.shutdown();
		RateLimiterUtil.cleanup();
	}
}