package org.example.grailedmldemo.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.model.GrailedListingRaw;

import java.util.List;

//Using jackson for JSON parsing of Grailed listing pages to extract relevant data fields (title, price, brand, etc.)
public class GrailedListingParser implements Parser {
    ObjectMapper mapper = new ObjectMapper();
    // Extracts the "hits" (info) array from the Algolia response
    // Maps each hit to a ListingRaw object
    @Override
    public List<GrailedListingRaw> parse(String rawJson) throws ParseException {
        return List.of();
    }
}
