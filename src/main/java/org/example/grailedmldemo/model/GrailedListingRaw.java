package org.example.grailedmldemo.model;
import lombok.Builder;

import java.util.List;

//raw fields corresponding to the scraped data from the Grailed website, which will then be parsed and normalized.
@Builder
public class GrailedListingRaw {
    // Core identity
    private String id;                    // "87939511"
    private String title;                 // "Helmut Lang 98' Jeans"
    private String marketplace;           // "grailed"

    // Item details
    String category;              // "bottoms"
    String categoryPath;          // "bottoms.denim"
    String categoryPathSize;     // "bottoms.denim.32"
    String categorySize;          // "bottoms.32"
    String size;                  // "32"
    String color;                // "blue"
    String condition;            // "is_gently_used"
    String department;            // "menswear"
    String location;              // "United States"
    List<String> styles;          // ["streetwear"]

    // Designer
    String designerNames;         // "Helmut Lang"
    int designerId;               // 9712

    // Pricing
    int price;                    // 169 (final listed price)
    int soldPrice;                // 169
    List<Integer> price_drops;     // [192, 179, 169]
    boolean soldPriceIncludesShipping;  // false
    int soldShippingPrice;        // 15

    // Timestamps
    String createdAt;             // "2025-11-16T19:34:24.837Z"
    String soldAt;                // "2026-03-13T03:20:11.040Z"  ← the one you asked about
    String bumpedAt;              // "2025-12-10T23:51:45.768Z"
    String priceUpdatedAt;        // "2025-12-10T23:50:57.046Z"
    long createdAtI;              // unix timestamp version
    long soldAtI;                 // unix timestamp version

    // Seller
    int sellerTotalSales;         // 25
    double sellerRatingAverage;   // 4.916...
    int sellerRatingCount;        // 12
    boolean trustedSeller;        // true

    // Engagement
    int followerno;               // 209 (followers)
    double heatF;                 // 4364.59 (Grailed's demand score)
    int photoCount;               // 10
    int measurementCount;         // 5

    // Flags
    boolean sold;                 // true
    boolean dropped;              // true (had price drops)

    // Photo
    String coverPhotoUrl;         // the image URL
}
