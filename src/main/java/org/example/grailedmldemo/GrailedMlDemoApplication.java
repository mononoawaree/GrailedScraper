package org.example.grailedmldemo;

import okhttp3.OkHttpClient;
import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.fetcher.GrailedListingFetcher;

import java.io.IOException;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "";
        GrailedListingFetcher fetcher = new GrailedListingFetcher(client, "https://mnrwefss2q-dsn.algolia.net/1/indexes/*/queries?x-algolia-agent=Algolia%20for%20JavaScript%20(4.14.3)%3B%20Browser%3B%20instantsearch.js%20(4.75.5)%3B%20react%20(18.2.0)%3B%20react-instantsearch%20(7.13.8)%3B%20react-instantsearch-core%20(7.13.8)%3B%20next.js%20(14.2.33)%3B%20JS%20Helper%20(3.22.5)","MzFiOTg0YzlhZGNiNzk4ZDc0Y2FmMDNkZGU4Njc1ODcyYTEzZjM4NzBkNzkxY2VjNmIzMDc0M2FlZTViYmI0ZXZhbGlkVW50aWw9MTc3MzQzODAzMiZ1c2VyVG9rZW49Nzc0NDA0NQ==",
                "MNRWEFSS2Q");
        try {
            json = fetcher.fetch("Helmut Lang", 0);
        } catch (FetchException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println(json);
    }

}
