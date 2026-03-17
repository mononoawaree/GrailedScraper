package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.config.AlgoliaConfig;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.GrailedListingFetcher;
import org.example.grailedmldemo.model.GrailedListingRaw;
import org.example.grailedmldemo.parser.GrailedListingParser;

import java.io.IOException;
import java.util.List;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws IOException, ParseException {
        String json;
        String baseUrl = AlgoliaConfig.getBaseUrl();
        System.out.println("Base URL: " + baseUrl);
        String apiKey = AlgoliaConfig.getApiKey();
        String appId = AlgoliaConfig.getAppId();
        OkHttpClient client = new OkHttpClient();
        GrailedListingFetcher fetcher = new GrailedListingFetcher(client, baseUrl, apiKey, appId);
        try {
            json = fetcher.fetch("Helmut Lang", 0);
        } catch (FetchException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        GrailedListingParser parser = new GrailedListingParser();
        List<GrailedListingRaw> lisings = parser.parse(json);
        System.out.println("Listings:");
        for (GrailedListingRaw listing : lisings) {
            System.out.println(listing.getUser().getSeller_score().getRating_average());
        }
    }
}
