package org.delicias.rest.clients;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.delicias.common.dto.restaurant.RestaurantResumeDTO;
import org.delicias.rest.filter.AuthorizationRequestFilter;
import org.delicias.rest.filter.UserTokenPropagation;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/api/restaurants")
@RegisterRestClient(configKey = "restaurants-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(AuthorizationRequestFilter.class)
@UserTokenPropagation
public interface RestaurantClient {

    @GET
    @Path("/batch")
    List<RestaurantResumeDTO> getRestaurantsByIds(@QueryParam("ids") List<Integer> ids);
}
