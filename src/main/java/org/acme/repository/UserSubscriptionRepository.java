package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.UserSubscription;

@ApplicationScoped
public class UserSubscriptionRepository implements PanacheRepository<UserSubscription> {
    public UserSubscription findActiveSubscriptionByUserId(Long userId) {
        return find("SELECT u FROM UserSubscription u WHERE u.userId = 1").firstResult();
    }
}
