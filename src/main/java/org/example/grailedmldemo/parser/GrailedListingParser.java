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
        for (JsonElement element : hits) {
            JsonObject listing = element.getAsJsonObject();
            // Extract styles if available
            List<String> styles = new ArrayList<>();
            JsonArray stylesArray = listing.getAsJsonArray("styles");
            if (stylesArray != null) {
                for (JsonElement style : stylesArray) {
                    styles.add(style.getAsString());
                }
            }
            // Extract price drops if available
            List<Integer> priceDrops = new ArrayList<>();
            JsonArray priceDropsArray = listing.getAsJsonArray("styles");
            if (priceDropsArray != null) {
                for (JsonElement priceDrop : priceDropsArray) {
                    priceDrops.add(priceDrop.getAsInt());
                }
            }
            //
        GrailedListingRaw raw = GrailedListingRaw.builder()
                .id(listing.get("id").getAsString())
                .title(listing.get("title").getAsString())
                .marketplace(listing.get("marketplace").getAsString())
                .category(listing.get("category").getAsString())
                .categoryPath(listing.get("categoryPath").getAsString())
                .categoryPathSize(listing.get("categoryPathSize").getAsString())
                .categorySize(listing.get("categorySize").getAsString())
                .size(listing.get("size").getAsString())
                .color(listing.get("color").getAsString())
                .condition(listing.get("condition").getAsString())
                .department(listing.get("department").getAsString())
                .location(listing.get("location").getAsString())
                .styles(styles)
                .designerNames(listing.get("designerNames").getAsString())
                .price(listing.get("price").getAsInt())
                .soldPrice(listing.get("soldPrice").getAsInt())
                .priceDrops(priceDrops)
                .soldPriceIncludesShipping(listing.get("soldPriceIncludesShipping").getAsBoolean())
                .soldShippingPrice(listing.get("soldShippingPrice").getAsInt())
                .createdAt(listing.get("createdAt").getAsString())
                .soldAt(listing.get("soldAt").getAsString())
                .bumpedAt(listing.get("bumpedAt").getAsString())
                .priceUpdatedAt(listing.get("priceUpdatedAt").getAsString())
                .createdAtI(listing.get("createdAtI").getAsLong())
                .soldAtI(listing.get("soldAtI").getAsLong())
                .sellerTotalSales(listing.get("sellerTotalSales").getAsInt())
                .sellerRatingAverage(listing.get("sellerRatingAverage").getAsDouble())
                .sellerRatingCount(listing.get("sellerRatingCount").getAsInt())
                .trustedSeller(listing.get("trustedSeller").getAsBoolean())
                .followerno(listing.get("followerno").getAsInt())
                .heatF(listing.get("heatF").getAsDouble())
                .photoCount(listing.get("photoCount").getAsInt())
                .measurementCount(listing.get("measurementCount").getAsInt())
                .sold(listing.get("sold").getAsBoolean())
                .dropped(listing.get("dropped").getAsBoolean())
                .coverPhotoUrl(listing.get("coverPhotoUrl").getAsString())
                .build();
            grailedListingRaws.add(raw);
    }
        return List.of();
    }
}
