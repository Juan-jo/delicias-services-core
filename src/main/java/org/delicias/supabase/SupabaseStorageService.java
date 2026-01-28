package org.delicias.supabase;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.delicias.config.SupabaseConfig;
import org.delicias.rest.clients.SupabaseStorageClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@ApplicationScoped
public class SupabaseStorageService {

    @Inject
    SupabaseConfig config;

    @Inject
    @RestClient
    SupabaseStorageClient supabaseClient;

    public String uploadFile(FileUpload file) throws IOException {

        try {
            String safeFileName = file.fileName()
                    .replaceAll("[^a-zA-Z0-9._-]", "_")
                    .replaceAll("\\s+", "-");

            String fileName = UUID.randomUUID() + "-" + safeFileName;

            String encodedFileName = URLEncoder
                    .encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            byte[] fileBytes = Files.readAllBytes(file.uploadedFile());

            Response response = supabaseClient.upload(
                    config.bucket(),
                    encodedFileName,
                    fileBytes,
                    file.contentType(),
                    "Bearer " + config.key(),
                    config.key()
            );

            if (response.getStatus() / 100 == 2) {
                return "%s/storage/v1/object/public/%s/%s"
                        .formatted(config.url(), config.bucket(), encodedFileName);
            }

            String error = response.hasEntity()
                    ? response.readEntity(String.class)
                    : "<no body>";

            throw new RuntimeException("Supabase upload failed: " + error);
        }
        catch (jakarta.ws.rs.WebApplicationException e) {
            // Esto lee el JSON de error que viene de Supabase (ej: {"error":"Invalid bucket"})
            String realError = e.getResponse().readEntity(String.class);
            System.err.println("MENSAJE REAL DE SUPABASE: " + realError);
            throw e;
        }
    }

    public void deleteFile(String publicUrl) {

        String publicUrlPrefix = String.format(
                "%s/storage/v1/object/public/%s/",
                config.url(),
                config.bucket()
        );

        String filePath = publicUrl.replace(publicUrlPrefix, "");

        try {
            supabaseClient.delete(
                    config.bucket(),
                    filePath,
                    "Bearer " + config.key(),
                    config.key()
            );
        } catch (Exception e) {
            System.out.println("Err delete img in supabase -> " + e.getMessage());
        }
    }
}



