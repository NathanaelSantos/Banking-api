package org.acme.Exception;

public class SubscriptionExpiredException extends RuntimeException {
    public SubscriptionExpiredException(Long userId) {
        super("Subscription expired for user " +  userId);
    }
}
