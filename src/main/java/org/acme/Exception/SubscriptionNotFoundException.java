package org.acme.Exception;

public class SubscriptionNotFoundException extends RuntimeException{
    public SubscriptionNotFoundException(Long userId) {
        super("Subscription not found for user id " + userId);
    }
}
