package org.delicias.rest.clients;

import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.delicias.common.dto.restaurant.RestaurantResumeDTO;
import org.delicias.rest.filter.AuthorizationRequestFilter;
import org.delicias.rest.filter.UserTokenPropagation;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Path("/api/restaurants")
@RegisterRestClient(configKey = "restaurants-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(AuthorizationRequestFilter.class)
@UserTokenPropagation
public interface RestaurantClient {

    @GET
    @Path("/batch")
    @CacheResult(cacheName = "restaurants-batch-cache")
    @Retry(maxRetries = 2, delay = 300)
    @CircuitBreaker(
            requestVolumeThreshold = 20,
            failureRatio = 0.5,
            delay = 15000,
            successThreshold = 5
    )
    @Timeout(5000)
    @Fallback(RestaurantBatchFallback.class)
    List<RestaurantResumeDTO> getRestaurantsByIds(@QueryParam("ids") Set<Integer> ids);


    class RestaurantBatchFallback implements FallbackHandler<List<RestaurantResumeDTO>> {
        @Override
        public List<RestaurantResumeDTO> handle(ExecutionContext context) {
            // Default return
            return Collections.emptyList();
        }
    }
}
