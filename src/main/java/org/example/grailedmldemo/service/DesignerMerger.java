package org.example.grailedmldemo.service;

import java.util.*;

public class DesignerMerger {

    public static Map<String, String> merge(
            List<String[]> vestiaireBrands,  // [name, slug]
            List<String[]> grailedBrands     // [name, slug]
    ) {
        // vestiaire_slug -> grailed_slug
        Map<String, String> matched = new LinkedHashMap<>();
        Set<String> unmatchedVestiaire = new LinkedHashSet<>();

        // Build Grailed lookup maps
        Map<String, String> grailedBySlug = new HashMap<>();        // slug -> slug
        Map<String, String> grailedByName = new HashMap<>();        // lowercase name -> slug
        Map<String, String> grailedByStripped = new HashMap<>();    // stripped slug -> slug

        for (String[] brand : grailedBrands) {
            String name = brand[0];
            String slug = brand[1];
            grailedBySlug.put(slug, slug);
            grailedByName.put(name.toLowerCase().trim(), slug);
            grailedByStripped.put(strip(slug), slug);
        }

        for (String[] brand : vestiaireBrands) {
            String vName = brand[0];
            String vSlug = brand[1];

            // Stage 1: Exact slug match
            if (grailedBySlug.containsKey(vSlug)) {
                matched.put(vSlug, grailedBySlug.get(vSlug));
                continue;
            }

            // Stage 2: Exact name match (case-insensitive)
            String nameLower = vName.toLowerCase().trim();
            if (grailedByName.containsKey(nameLower)) {
                matched.put(vSlug, grailedByName.get(nameLower));
                continue;
            }

            // Stage 3: Stripped slug match
            String vStripped = strip(vSlug);
            if (grailedByStripped.containsKey(vStripped)) {
                matched.put(vSlug, grailedByStripped.get(vStripped));
                continue;
            }

            // No match found
            unmatchedVestiaire.add(vSlug);
        }

        System.out.println("Stage 1-3 results:");
        System.out.println("  Matched: " + matched.size());
        System.out.println("  Unmatched: " + unmatchedVestiaire.size());
        System.out.println("  Total Vestiaire: " + vestiaireBrands.size());
        System.out.println("  Total Grailed: " + grailedBrands.size());

        return matched;
    }

    // Strip everything that isn't a letter or digit
    private static String strip(String slug) {
        return slug.replaceAll("[^a-z0-9]", "");
    }
}
