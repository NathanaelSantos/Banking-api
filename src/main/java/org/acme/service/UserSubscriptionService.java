package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.Exception.subscription.SubscriptionExpiredException;
import org.acme.Exception.subscription.SubscriptionNotActiveException;
import org.acme.Exception.subscription.SubscriptionNotFoundException;
import org.acme.Exception.subscription.SubscriptionPlanNotValidException;
import org.acme.model.UserSubscription;
import org.acme.repository.UserSubscriptionRepository;

import java.util.Date;
import java.util.logging.Logger;

@ApplicationScoped
public class UserSubscriptionService {

    private static final Logger log = Logger.getLogger(UserSubscriptionService.class.getName());

    @Inject
    UserSubscriptionRepository userSubscriptionRepository;

    public boolean isSubscriptionValid(Long userId){
        UserSubscription subscription = userSubscriptionRepository.findActiveSubscriptionByUserId(userId);

        if(subscription == null){
            log.warning("Subscription not found for userId: " + userId);
            throw new SubscriptionNotFoundException(userId);
        }

        if(!"ACTIVE".equals(subscription.getStatus())){
            log.warning("Subscription for userId: " + userId + " is not active");
            throw new SubscriptionNotActiveException(userId);
        }

        // Check if the subscription is still valid (not expired)
        if(subscription.getValidUntil() == null || subscription.getValidUntil().before(new Date())){
            log.warning("Subscription for userId: " + userId + " has expired or the expiry date is null");
            throw new SubscriptionExpiredException(userId);
        }

        // Check if the user's plan is adequate
        if(!"PREMIUM".equalsIgnoreCase(subscription.getPlan())){
            log.warning("Subscription for userId: " + userId + " is not premium");
            throw new SubscriptionPlanNotValidException(userId);
        }

        return true;
    }
}
