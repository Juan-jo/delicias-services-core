package org.delicias.rest.filter;


import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import org.eclipse.microprofile.jwt.JsonWebToken;

@UserTokenPropagation
public class AuthorizationRequestFilter implements ClientRequestFilter {

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ClientRequestContext requestContext) {
        if (jwt != null && jwt.getRawToken() != null) {
            requestContext
                    .getHeaders()
                    .add("Authorization", "Bearer " + jwt.getRawToken());
        }
    }
}
