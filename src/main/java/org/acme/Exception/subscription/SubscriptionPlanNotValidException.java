package org.acme.Exception.subscription;

public class SubscriptionPlanNotValidException extends RuntimeException {
    public SubscriptionPlanNotValidException(Long userId) {
        super(String.format("Subscription plan is not valid. User ID: %s", userId));
    }
}
