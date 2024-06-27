package org.acme;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Exception.SubscriptionExpiredException;
import org.acme.Exception.SubscriptionNotActiveException;
import org.acme.Exception.SubscriptionNotFoundException;
import org.acme.Exception.SubscriptionPlanNotValidException;
import org.acme.model.BankModel;
import org.acme.service.BankService;
import org.acme.service.SubscriptionService;

import java.util.List;

@Path("/banks")
@Produces("application/json")
@Consumes("application/json")
public class Application {

    @Inject
    BankService bankService;
    @Inject
    SubscriptionService subscriptionService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "premium"})
    public Response getAllBanks() {
        try {
            if(subscriptionService.isSubscriptionValid(getCurrentUserId())){
                List<BankModel> bank = bankService.getAllBanks();
                return Response.ok(bank).build();
            }
        } catch (SubscriptionNotFoundException | SubscriptionNotActiveException | SubscriptionExpiredException | SubscriptionPlanNotValidException e) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }

        return Response.status(Response.Status.FORBIDDEN).entity("{\"error\": \"Access denied.\"}").build();
    }

    private Long getCurrentUserId() {
        return 1L;
    }
}
