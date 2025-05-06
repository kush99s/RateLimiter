package Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Factory.RateLimiterFactory;
import RateLimiter.RateLimiter;

public class RateLimiterService {
	Map<String,RateLimiter> userRateLimiter = new ConcurrentHashMap<>();	
	
	public void registerUser(String userId, String algorithm, int capacity,long windowSize) {
		userRateLimiter.put(userId, RateLimiterFactory.createRateLimiter(algorithm, capacity, windowSize));
	}
	
	public boolean allowRequest(String userId) {
		RateLimiter limiter = userRateLimiter.get(userId);
		if (limiter == null) throw new IllegalArgumentException("User not registered");
		return limiter.allowRequest(userId);
	}
}
