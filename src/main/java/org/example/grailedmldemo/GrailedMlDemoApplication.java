package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.VestiaireListingFetcher;
import org.example.grailedmldemo.model.VestiaireListingRaw;
import org.example.grailedmldemo.parser.VestiaireListingParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws IOException, ParseException, SQLException, FetchException {
        /*
        OkHttpClient client = new OkHttpClient();
        String baseUrl = AlgoliaConfig.getBaseUrl();
        String apiKey = AlgoliaConfig.getApiKey();
        String appId = AlgoliaConfig.getAppId();
        DataSource ds = DatabaseConfig.getDataSource();
        GrailedListingFetcher listingFetcher = new GrailedListingFetcher(client, baseUrl, apiKey, appId);
        GrailedDesignerFetcher designerFetcher = new GrailedDesignerFetcher(client);
        GrailedDesignerParser designerParser = new GrailedDesignerParser();
        GrailedListingParser listingParser = new GrailedListingParser();
        GrailedListingNormalizer listingNormalizer = new GrailedListingNormalizer();
        ListingRepository listingRepository = new ListingRepository(ds);
        DesignerRepository designerRepository = new DesignerRepository(ds);
        GrailedScrapeService grailedScrapeService = new GrailedScrapeService(listingFetcher, designerFetcher, designerParser, listingParser, listingNormalizer, listingRepository, designerRepository, ds);
        ScrapeController scrapeController = new ScrapeController(grailedScrapeService);
        scrapeController.run();
         */
        OkHttpClient client = new OkHttpClient();
        VestiaireListingFetcher vestiaireListingFetcher = new VestiaireListingFetcher("https://search.vestiairecollective.com/v1/product/search?=&=", client);
        String json;
        List<VestiaireListingRaw> vestiaireListingRaws;
        try {
            json = vestiaireListingFetcher.fetch("stone island", 60);
        } catch (FetchException e) {
            json = e.getMessage();
        }
        System.out.println(json);
    }
}
