package com.rt.service;

/* Objective: Implement Rate Limiter (Throttling Controller) for order() API per client basis
 * 1. Ratelimit incoming requests per client basis depends on license type
 * 2. Queue them if more requests are coming
 * 3. Timeout if requests are taking more than 20 seconds
 * 
 * Solution:
 * 1. Validate incoming request
 * 2. Submit OrderTask to a executor service
 * 3. Call future.get() with 20 sec timeout
 * 4. While processing OrderTask acquire semaphore and process
 */

public interface OrderService {
	public String order(final String clientToken, final String orderId);
	
	public void cleanup();
}
