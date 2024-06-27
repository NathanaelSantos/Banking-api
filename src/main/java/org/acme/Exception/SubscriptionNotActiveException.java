package org.acme.Exception;

public class SubscriptionNotActiveException extends RuntimeException {
    public SubscriptionNotActiveException(Long userId) {
        super("Subscription not active: " + userId);
    }
}
