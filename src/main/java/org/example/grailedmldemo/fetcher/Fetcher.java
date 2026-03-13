package org.example.grailedmldemo.fetcher;
import org.example.grailedmldemo.exception.FetchException;

public interface Fetcher {
    String fetch(String url, int page) throws FetchException;
}
