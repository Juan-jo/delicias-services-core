package org.delicias.rest.clients;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/storage/v1/object")
@RegisterRestClient(configKey = "supabase-service")
public interface SupabaseStorageClient {

    @PUT
    @Path("/{bucket}/{filePath:.+}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    Response upload(
            @PathParam("bucket") String bucket,
            @PathParam(value = "filePath") String filePath,
            byte[] body,
            @HeaderParam("Content-Type") String contentType,
            @HeaderParam("Authorization") String authorization,
            @HeaderParam("apikey") String apiKey
            );

    @DELETE
    @Path("/{bucket}/{filePath}")
    Response delete(
            @PathParam("bucket") String bucket,
            @PathParam("filePath") String filePath,
            @HeaderParam("Authorization") String authorization,
            @HeaderParam("apikey") String apiKey
    );
}
