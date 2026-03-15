package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.config.AlgoliaConfig;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.GrailedListingFetcher;
import org.example.grailedmldemo.parser.GrailedListingParser;

import java.io.IOException;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws IOException, ParseException {
        String baseUrl = AlgoliaConfig.getBaseUrl();
        System.out.println("Base URL: " + baseUrl);
        String apiKey = AlgoliaConfig.getApiKey();
        String appId = AlgoliaConfig.getAppId();
        OkHttpClient client = new OkHttpClient();
        String json = "";
        GrailedListingFetcher fetcher = new GrailedListingFetcher(client, baseUrl, apiKey, appId);
        try {
            json = fetcher.fetch("Helmut Lang", 0);
        } catch (FetchException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        GrailedListingParser parser = new GrailedListingParser();
        parser.parse(json).forEach(System.out::println);
    }

}
