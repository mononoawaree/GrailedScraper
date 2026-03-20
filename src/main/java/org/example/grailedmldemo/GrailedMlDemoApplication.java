package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.config.AlgoliaConfig;
import org.example.grailedmldemo.config.DatabaseConfig;
import org.example.grailedmldemo.controller.ScrapeController;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.service.GrailedScrapeService;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.GrailedDesignerFetcher;
import org.example.grailedmldemo.fetcher.GrailedListingFetcher;
import org.example.grailedmldemo.normalizer.GrailedListingNormalizer;
import org.example.grailedmldemo.parser.GrailedDesignerParser;
import org.example.grailedmldemo.parser.GrailedListingParser;
import org.example.grailedmldemo.repository.DesignerRepository;
import org.example.grailedmldemo.repository.ListingRepository;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws IOException, ParseException, SQLException, FetchException {
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
    }
}
