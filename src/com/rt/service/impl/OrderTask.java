package com.rt.service.impl;

import java.util.concurrent.Callable;

import com.rt.db.OrderDAO;
import com.rt.service.impl.util.RateLimiterUtil;

public class OrderTask implements Callable<String> {
	final String clientToken;
	final String orderId;

	OrderTask(final String clientToken, final String orderId) {
		this.clientToken = clientToken;
		this.orderId = orderId;
	}

	public String call() throws InterruptedException {
		RateLimiter rt = RateLimiterUtil.getRateLimiter(clientToken);
		rt.acquire();
		return OrderDAO.readOrder(orderId);
	}
}
