package com.rt.service.impl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import com.rt.service.impl.util.RateLimiterUtil;

public class RateLimiter {
	private int limit;
	private int timeUnits;// Always in seconds
	private long lastVisit = System.currentTimeMillis();
	private Semaphore semaphore;
	private Timer timer = new Timer();

	class ResetSemaphore extends TimerTask {
		public void run() {
			semaphore.release(limit);
	    }
	}

	RateLimiter(final int timeUnits, final int limit) {
		this.timeUnits = timeUnits;
		this.limit = limit;
		semaphore = new Semaphore(limit, true);
		timer.schedule(new ResetSemaphore(), timeUnits*1000);
	}

	public static RateLimiter createRateLimiter(final String clientToken) {
		RateLimiter rl = new RateLimiter(1, RateLimiterUtil.fetchLicenseType(clientToken).limitsPerSecond());
		return rl;
	}

	public void acquire() throws InterruptedException {
		/*long current = System.currentTimeMillis();
		// Time passed from last reset
		long timePassed = TimeUnit.MILLISECONDS.toSeconds(current - lastVisit);
		if (timePassed > timeUnits) {
			semaphore.release(limit);
			lastVisit = current;// updating reset time
		}*/
		semaphore.acquire();
	}
	
	public void cleaup() {
		timer.cancel();
	}


}
