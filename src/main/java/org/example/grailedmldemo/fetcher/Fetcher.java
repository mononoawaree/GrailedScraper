package org.example.grailedmldemo.fetcher;
import org.example.grailedmldemo.exception.FetchException;

import java.io.IOException;

public interface Fetcher {
    String fetch(String url, int page) throws FetchException, IOException;
}
