package org.example.grailedmldemo.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.model.GrailedListingRaw;

import java.util.ArrayList;
import java.util.List;

//Using jackson for JSON parsing of Grailed listing pages to extract relevant data fields (title, price, brand, etc.)
public class GrailedListingParser implements Parser {
    ObjectMapper mapper = new ObjectMapper();
    // Extracts the "hits" (info) array from the Algolia response
    // Maps each hit to a ListingRaw object
    @Override
    public List<GrailedListingRaw> parse(String rawJson) throws ParseException {
        List<GrailedListingRaw> grailedListingRaws = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(rawJson).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");
        JsonObject firstResult = results.get(0).getAsJsonObject();
        JsonArray hits = firstResult.getAsJsonArray("hits");
        return List.of();
    }
}
