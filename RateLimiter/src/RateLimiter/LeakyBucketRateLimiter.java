package RateLimiter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeakyBucketRateLimiter implements RateLimiter {
	

	int capacity;
    long leakRateSecs;
    Queue<Long> bucket = new LinkedList<>();
    ScheduledExecutorService scheduledExecutorService;

    public LeakyBucketRateLimiter(int capacity, long leakRateSecs) {
		this.capacity = capacity;
		this.leakRateSecs = leakRateSecs;
		this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(this::leakRequests, 0, leakRateSecs, TimeUnit.SECONDS);
	}
    
	@Override
	public boolean allowRequest(String userId) {
		long currentTime = System.currentTimeMillis();
		if(bucket.size()<capacity) {
			bucket.add(currentTime);
			return true;
		}
		return false;
	}
	
	private synchronized void leakRequests() {
		if(!bucket.isEmpty()) {
			bucket.poll();
		}
	}

}
