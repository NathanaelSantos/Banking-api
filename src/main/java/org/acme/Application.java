package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Exception.subscription.SubscriptionExpiredException;
import org.acme.Exception.subscription.SubscriptionNotActiveException;
import org.acme.Exception.subscription.SubscriptionNotFoundException;
import org.acme.Exception.subscription.SubscriptionPlanNotValidException;
import org.acme.model.BankModel;
import org.acme.service.BankService;
import org.acme.service.UserSubscriptionService;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/banks")
@Produces("application/json")
@Consumes("application/json")
public class Application {

    @Inject
    BankService bankService;
    @Inject
    UserSubscriptionService subscriptionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Get all banks", description = "Operations related to banks")
    @Operation(summary = "Get all banks", description = "Returns a list of all banks if the user's subscription is valid")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "List of banks",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BankModel.class, type = SchemaType.ARRAY))
            )
    })
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

