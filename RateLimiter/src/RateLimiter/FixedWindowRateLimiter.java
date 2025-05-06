package RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter implements RateLimiter {

	int maxAllowedRequest;
	ConcurrentHashMap<String,Integer> requestCounts=new ConcurrentHashMap<>();
	long windowStart;
	long windowSizeMillis;
	
	public FixedWindowRateLimiter(int maxAllowedRequest,long windowSizeMillis){
		this.maxAllowedRequest = maxAllowedRequest;
		this.windowSizeMillis = windowSizeMillis;
		this.windowStart = System.currentTimeMillis();
	}
	@Override
	public boolean allowRequest(String userId) {
		long currentTime = System.currentTimeMillis();
		if(currentTime-windowStart>=windowSizeMillis) {
			requestCounts.remove(userId);
			windowStart = currentTime;
		}
		requestCounts.put(userId, requestCounts.getOrDefault(userId, 0)+1);
		return requestCounts.get(userId)<=maxAllowedRequest;
	}
}
