package RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

	int capacity;
	double refillRate;
	ConcurrentHashMap<String, Integer> tokens = new ConcurrentHashMap<>();
	ConcurrentHashMap<String, Long> lastRefillTimeStamp = new ConcurrentHashMap<>();
	
	public TokenBucketRateLimiter(int capacity, double refillRate) {
		this.capacity = capacity;
		this.refillRate = refillRate;
	}
	
	@Override
	public boolean allowRequest(String userId) {
		long currentTime = System.currentTimeMillis();
		lastRefillTimeStamp.putIfAbsent(userId, currentTime);
		tokens.putIfAbsent(userId, capacity);
		long elapsedTime = currentTime - lastRefillTimeStamp.get(userId);
		if(elapsedTime>0) {
			int newTokens= Math.min(capacity, tokens.get(userId)+ (int)(refillRate*elapsedTime));
			tokens.put(userId, newTokens);
			lastRefillTimeStamp.put(userId, currentTime);
		}
		if (tokens.get(userId)>0) {
			tokens.put(userId, tokens.get(userId)-1);
			return true;
		}
		return false;
	}

}
