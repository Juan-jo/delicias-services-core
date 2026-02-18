package org.delicias.rest.clients;

import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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

    class UserZoneFallback implements FallbackHandler<UserZoneDTO> {
        @Override
        public UserZoneDTO handle(ExecutionContext context) {
            // Retorna una zona por defecto o un objeto que indique "Zona Desconocida"
            // para no romper el flujo del microservicio que llama.
            return new UserZoneDTO(null, 0);
        }
    }

}
