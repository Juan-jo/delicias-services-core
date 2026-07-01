package org.delicias.rest.clients;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.rest.filter.AuthorizationRequestFilter;
import org.delicias.rest.filter.UserTokenPropagation;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@Path("/api/notifications")
@RegisterRestClient(configKey = "notifications-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(AuthorizationRequestFilter.class)
@UserTokenPropagation
public interface NotificationClient {

    @POST
    Response sendNotificacion(Map<String, Object> body);
}
