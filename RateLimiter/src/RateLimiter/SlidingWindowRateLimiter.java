package RateLimiter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {

	ConcurrentHashMap<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();
	int maxAllowedRequests;
	long windowSizeMillis;
	
	public SlidingWindowRateLimiter(int maxAllowedRequests, long windowSizeMillis) {
		this.maxAllowedRequests = maxAllowedRequests;
		this.windowSizeMillis = windowSizeMillis;
	}
	
	@Override
	public boolean allowRequest(String userId) {
		long currentTime = System.currentTimeMillis();
		requestLogs.putIfAbsent(userId, new LinkedList<>());
		Deque<Long> timeStamps = requestLogs.get(userId);
		while(timeStamps.size()>0 && currentTime-timeStamps.peekFirst()>windowSizeMillis) {
			timeStamps.pollFirst();
		}
		if(timeStamps.size()<maxAllowedRequests) {
			timeStamps.add(currentTime);
			requestLogs.put(userId,timeStamps);
			return true;
		}

		return false;
	}

}
