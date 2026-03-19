package org.example.grailedmldemo.fetcher;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.grailedmldemo.exception.FetchException;

import java.io.IOException;

public class GrailedDesignerFetcher {
    private final OkHttpClient client;

    public GrailedDesignerFetcher(OkHttpClient client) {
        this.client = client;
    }

    public String fetch(String url) throws FetchException, IOException {
        String json = null;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                json = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
