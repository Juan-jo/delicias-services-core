package org.delicias.rest.clients;

import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.common.dto.product.ProductResumeDTO;
import org.delicias.rest.filter.AuthorizationRequestFilter;
import org.delicias.rest.filter.UserTokenPropagation;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Path("/api/products")
@RegisterRestClient(configKey = "products-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(AuthorizationRequestFilter.class)
@UserTokenPropagation
public interface ProductClient {

    @GET
    @Path("/batch")
    @CacheResult(cacheName = "products-batch-cache")
    @Retry(maxRetries = 3, delay = 200)
    @CircuitBreaker(
            requestVolumeThreshold = 10,
            failureRatio = 0.5,
            delay = 10000,
            successThreshold = 3
    )
    @Timeout(4000)
    @Fallback(ProductBatchFallback.class)
    List<ProductResumeDTO> getProductsByIds(@QueryParam("ids") Set<Integer> ids);

    @GET
    @Path("/{productTmplId}/candidate-shoppingcart")
    @CacheResult(cacheName = "product-candidate-cache")
    Response getProductCandidateById(
            @PathParam("productTmplId") Integer productTmpId);

    @GET
    @Path("/{productTmplId}/prices")
    @CacheResult(cacheName = "product-prices-cache")
    Response getProductTmplPrice(
            @PathParam("productTmplId") Integer productTmpId);

    @GET
    @Path("/prices")
    @Retry(maxRetries = 3, delay = 200)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 5000)
    Response getProductTmplPrices(@QueryParam("ids") Set<Integer> ids);




    class ProductBatchFallback implements FallbackHandler<List<ProductResumeDTO>> {
        @Override
        public List<ProductResumeDTO> handle(ExecutionContext context) {
            // Default return
            return Collections.emptyList();
        }
    }
}
