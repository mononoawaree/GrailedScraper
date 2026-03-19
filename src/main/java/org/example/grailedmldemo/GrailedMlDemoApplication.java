package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.config.AlgoliaConfig;
import org.example.grailedmldemo.config.DatabaseConfig;
import org.example.grailedmldemo.config.GrailedApiConfig;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.GrailedDesignerFetcher;
import org.example.grailedmldemo.fetcher.GrailedListingFetcher;
import org.example.grailedmldemo.model.GrailedDesignerRaw;
import org.example.grailedmldemo.model.GrailedListingRaw;
import org.example.grailedmldemo.model.Listing;
import org.example.grailedmldemo.normalizer.GrailedListingNormalizer;
import org.example.grailedmldemo.parser.GrailedDesignerParser;
import org.example.grailedmldemo.parser.GrailedListingParser;
import org.example.grailedmldemo.repository.ListingRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws IOException, ParseException {
        /*
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
        List<GrailedListingRaw> rawListings = parser.parse(json);
        List<Listing> listings = new ArrayList<>();
        GrailedListingNormalizer normalizer = new GrailedListingNormalizer();
        for (GrailedListingRaw listingRaw : rawListings) {
            Listing listing = normalizer.normalize(listingRaw);
            listings.add(listing);
        }
        ListingRepository repository = new ListingRepository(DatabaseConfig.getDataSource());
        try {
            repository.saveAll(listings);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
        String designersJson;
        String designersUrl = GrailedApiConfig.getDesignersUrl();
        System.out.println("Designers URL: " + designersUrl);
        OkHttpClient designersClient = new OkHttpClient();
        GrailedDesignerFetcher designersFetcher = new GrailedDesignerFetcher(designersClient);
        try {
            designersJson = designersFetcher.fetch(designersUrl);
        } catch (FetchException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        GrailedDesignerParser designersParser = new GrailedDesignerParser();
        List<GrailedDesignerRaw> designers = designersParser.parse(designersJson);

    }
}
