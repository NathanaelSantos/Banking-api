package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.Exception.SubscriptionExpiredException;
import org.acme.Exception.SubscriptionNotActiveException;
import org.acme.Exception.SubscriptionNotFoundException;
import org.acme.Exception.SubscriptionPlanNotValidException;
import org.acme.model.UserSubscription;
import org.acme.repository.UserSubscriptionRepository;

import java.util.Date;
import java.util.logging.Logger;

@ApplicationScoped
public class SubscriptionService {

    private static final Logger log = Logger.getLogger(SubscriptionService.class.getName());

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

        //Verificar se a assinatura ainda é válida (não expirou):
        if(subscription.getValidUntil() == null || subscription.getValidUntil().before(new Date())){
            log.warning("Subscription for userId: " + userId + " has expired or the expiry date is null");
            throw new SubscriptionExpiredException(userId);
        }

        //Verificar se o plano do usuário é adequado:
        if(!"PREMIUM".equalsIgnoreCase(subscription.getPlan())){
            log.warning("Subscription for userId: " + userId + " is not premium");
            throw new SubscriptionPlanNotValidException(userId);
        }

        return true;
    }
}
