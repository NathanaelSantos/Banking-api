package org.acme.Exception.subscription;

public class SubscriptionExpiredException extends RuntimeException {
    public SubscriptionExpiredException(Long userId) {
        super("Subscription expired for user " +  userId);
    }
}
