import Service.RateLimiterService;

public class Main {

	public static void main(String[] args) {
		RateLimiterService service = new RateLimiterService();

        service.registerUser("user1", "fixed", 5, 10);
        service.registerUser("user2", "sliding", 3, 5);
        service.registerUser("user3", "token-bucket", 5, 10);
        service.registerUser("user4", "leaky-bucket", 3, 4);

        for (int i = 0; i < 7; i++) {
            System.out.println("User 1 Request " + (i + 1) + " : " + service.allowRequest("user1"));
            System.out.println("User 2 Request " + (i + 1) + " : " + service.allowRequest("user2"));
            System.out.println("User 3 Request " + (i + 1) + " : " + service.allowRequest("user3"));
            System.out.println("User 4 Request " + (i + 1) + " : " + service.allowRequest("user4"));
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

}
