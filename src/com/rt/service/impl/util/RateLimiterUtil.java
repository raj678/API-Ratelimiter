package com.rt.service.impl.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rt.db.LicenseType;
import com.rt.service.impl.RateLimiter;

public class RateLimiterUtil {
	final static Map<String, RateLimiter> rtMap = new ConcurrentHashMap<String, RateLimiter>();

	public static RateLimiter getRateLimiter(final String clientToken) {
		RateLimiter rt = rtMap.get(clientToken);
		if (rt == null) {
			synchronized (clientToken.intern()) {
				rt = rtMap.get(clientToken);
				if (rt == null) {
					rt = RateLimiter.createRateLimiter(clientToken);
					rtMap.put(clientToken.intern(), rt);
				}
			}
		}
		return rt;
	}

	// Usually this is another service call to fetch client info
	public static LicenseType fetchLicenseType(final String clientToken) {
		LicenseType[] lTypes = LicenseType.values();
		return lTypes[clientToken.hashCode() % 2];
	}

	public static void cleanup() {
		for(RateLimiter rt: rtMap.values()) {
			rt.cleaup();
		}
		rtMap.clear();
	}


}
