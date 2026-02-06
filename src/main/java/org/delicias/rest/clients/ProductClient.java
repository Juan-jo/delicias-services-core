package org.delicias.rest.clients;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.delicias.common.dto.RestaurantMenuProductDTO;
import org.delicias.rest.filter.AuthorizationRequestFilter;
import org.delicias.rest.filter.UserTokenPropagation;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/api/products")
@RegisterRestClient(configKey = "products-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(AuthorizationRequestFilter.class)
@UserTokenPropagation
public interface ProductClient {

    @GET
    @Path("/batch")
    List<RestaurantMenuProductDTO> getProductsByIds(@QueryParam("ids") List<Integer> ids);
}
