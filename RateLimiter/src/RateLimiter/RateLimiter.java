package RateLimiter;

public interface RateLimiter {
	public boolean allowRequest(String userId);
}	
