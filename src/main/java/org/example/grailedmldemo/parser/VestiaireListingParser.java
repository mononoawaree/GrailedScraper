package org.example.grailedmldemo.parser;

import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.model.GrailedListingRaw;

import java.util.List;

public class VestiaireListingParser implements Parser {

    @Override
    public List<GrailedListingRaw> parse(String rawJson) throws ParseException {
        return List.of();
    }
}
