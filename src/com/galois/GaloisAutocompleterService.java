package com.galois;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GaloisAutocompleterService {

    @NotNull
    private String baseUrl;

    private HttpClient client;

    public GaloisAutocompleterService(@NotNull String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
    }

    public String getCompletion(String requestBody) {
        final String AUTOCOMPLETE_ROUTE = "autocomplete";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + AUTOCOMPLETE_ROUTE))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 ? response.body() : null;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
