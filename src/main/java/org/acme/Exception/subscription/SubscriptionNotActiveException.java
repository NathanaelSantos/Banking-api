package org.acme.Exception.subscription;

public class SubscriptionNotActiveException extends RuntimeException {
    public SubscriptionNotActiveException(Long userId) {
        super("Subscription not active: " + userId);
    }
}
