package org.example.grailedmldemo.fetcher;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.exception.FetchException;

//implementation (uses OkHttp) of the fetcher interface to retrieve HTML content from Grailed listing pages. This class will handle the HTTP requests and responses, including error handling and retries if necessary.
// It may also include logic for handling rate limits or using proxies if required by the Grailed API or website.
public class GrailedListingFetcher implements Fetcher{
    private OkHttpClient client;
    private String apiKey;
    private String appId;
    // Builds the POST request with correct headers and body
    // Returns raw JSON string for the parser to handle
    @Override
    public String fetch(String url, int page) throws FetchException {
        return "";
    }
}
