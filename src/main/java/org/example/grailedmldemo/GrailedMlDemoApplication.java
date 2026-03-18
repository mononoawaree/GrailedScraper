package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.config.AlgoliaConfig;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.GrailedListingFetcher;
import org.example.grailedmldemo.model.GrailedListingRaw;
import org.example.grailedmldemo.model.Listing;
import org.example.grailedmldemo.normalizer.GrailedListingNormalizer;
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
        List<GrailedListingRaw> listings = parser.parse(json);
        GrailedListingNormalizer normalizer = new GrailedListingNormalizer();
        for (GrailedListingRaw listingRaw : listings) {
            Listing listing = normalizer.normalize(listingRaw);
            System.out.println(listing.getTitle()+ " " + listing.getCategory() + " " + listing.getCategoryPath() + " " + listing.getCategorySize() + " " + listing.getCategoryPathSize());
        }
        /*
        System.out.println("Listings:");
        int counter = 0;
        for (GrailedListingRaw listing : listings) {
            counter++;
            System.out.println(counter);
            for (GrailedListingRaw.Trait trait : listing.getTraits()) {
                System.out.println(trait.getName() + ": " + trait.getValue());
            }
        }
         */
    }
}
