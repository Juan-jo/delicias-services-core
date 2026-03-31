package org.delicias.rest.clients;

import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.common.dto.user.UserZoneDTO;
import org.delicias.rest.filter.AuthorizationRequestFilter;
import org.delicias.rest.filter.UserTokenPropagation;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/api/users")
@RegisterRestClient(configKey = "users-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(AuthorizationRequestFilter.class)
@UserTokenPropagation
public interface UserClient {

    @GET
    @Path("/{id}/zone")
    @CacheResult(cacheName = "user-zone-cache")
    @Retry(maxRetries = 3, delay = 200)
    @CircuitBreaker(
            requestVolumeThreshold = 10,
            failureRatio = 0.5,
            delay = 10000,
            successThreshold = 3
    )
    @Timeout(2000)
    @Fallback(UserZoneFallback.class)
    UserZoneDTO getUserZone(@PathParam("id") UUID userId);

    @GET
    @Path("/addresses/{addressId}/shopping")
    @Retry(maxRetries = 3, delay = 200)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 5000)
    @Fallback(AddressFallback.class)
    Response getUserAddress(@PathParam("addressId") Integer addressId);


    @GET
    @Path("/addresses/default")
    @Retry(maxRetries = 3, delay = 200)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 5000)
    @Fallback(AddressFallback.class)
    Response getUserAddressDefault();


    @GET
    @Path("/addresses/{addressId}/fields")
    Response getByFields(
            @PathParam("addressId") Integer addressId,
            @QueryParam("fields") String fields
    );






    class UserZoneFallback implements FallbackHandler<UserZoneDTO> {
        @Override
        public UserZoneDTO handle(ExecutionContext context) {
            return new UserZoneDTO(null, 0);
        }
    }

    class AddressFallback implements FallbackHandler<Response> {
        @Override
        public Response handle(ExecutionContext context) {
            return Response.status(Response.Status.PARTIAL_CONTENT)
                    .header("X-Fallback", true)
                    .header("X-Fallback-Source", "Circuit-Breaker-Store")
                    .build();
        }
    }


}
