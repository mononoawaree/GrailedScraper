package org.example.grailedmldemo.fetcher;

import okhttp3.*;
import org.example.grailedmldemo.exception.FetchException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;



//implementation (uses OkHttp) of the fetcher interface to retrieve HTML content from Grailed listing pages. This class will handle the HTTP requests and responses, including error handling and retries if necessary.
// It may also include logic for handling rate limits or using proxies if required by the Grailed API or website.
public class GrailedListingFetcher implements Fetcher {
    private final OkHttpClient client;
    private final String baseUrl;
    private final String apiKey;
    private final String appId;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public GrailedListingFetcher(OkHttpClient client, String baseUrl, String apiKey, String appId) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.appId = appId;
    }

    // Builds the POST request
    // Returns raw JSON string for the parser to handle
    @Override
    public String fetch(String designerName, int page) throws FetchException, IOException {
        String encodedName = URLEncoder.encode(designerName, StandardCharsets.UTF_8);
        String params = "facetFilters=%5B%5B%22designers.name%3A"
                + encodedName
                + "%22%5D%5D"
                + "&hitsPerPage=40"
                + "&page=" + page
                + "&numericFilters=%5B%22sold_price%3E%3D0%22%2C%22sold_price%3C%3D1000000%22%5D"
                + "&query=";
        String body = "{\"requests\":[{\"indexName\":\"Listing_sold_production\",\"params\":\"" + params + "\"}]}";
        System.out.println("Request body: " + body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        Request request = new Request.Builder()
                .url(baseUrl)
                .addHeader("X-Algolia-API-Key", apiKey)
                .addHeader("X-Algolia-Application-Id", appId)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new FetchException("Failed: " + response.code());
            }
            return response.body().string();
        }
    }
}
