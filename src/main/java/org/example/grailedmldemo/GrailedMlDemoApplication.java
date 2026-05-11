package org.example.grailedmldemo;

import org.example.grailedmldemo.exception.FetchException;
import org.example.grailedmldemo.exception.ParseException;
import org.example.grailedmldemo.fetcher.GrailedDesignerFetcher;
import org.example.grailedmldemo.fetcher.VestiaireDesignerFetcher;
import org.example.grailedmldemo.model.Designer;
import org.example.grailedmldemo.model.VestiaireDesignerRaw;
import org.example.grailedmldemo.parser.GrailedDesignerParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GrailedMlDemoApplication {
    public static void main(String[] args) throws Exception, ParseException, FetchException {
        List<Designer> vs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("vestiaire_brands.txt"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                Designer vestiaire = new Designer();
                String[] parts = line.split("\t");
                if (parts.length < 4) {
                    System.out.println("Skipping: " + line);
                    continue;
                }
                vestiaire.setId(parts[0].trim());
                vestiaire.setName(parts[1].trim());
                vestiaire.setSlug(parts[2].trim());
                vestiaire.setCount(parts[3].trim());
                vs.add(vestiaire);
            }
            // After the file reading loop, add the ones that got skipped
            vs.add(new Designer("478", "zapa", "zapa", "2564", "vestiaire"));
            vs.add(new Designer("11924", "zu elements", "zu-elements", "178", "vestiaire"));
            vs.add(new Designer("15312", "zua", "zua", "6", "vestiaire"));
            vs.add(new Designer("9074", "ader error", "ader-error", "217","vestiaire"));
            vs.add(new Designer("11987", "donald duck disney x gucci", "donald-duck-disney-x-gucci", "122", "vestiaire"));
            vs.add(new Designer("235", "escada", "escada", "12470", "vestiaire"));
            vs.add(new Designer("523", "james perse", "james-perse", "854", "vestiaire"));
            vs.add(new Designer("9579", "nike x undercover", "nike-x-undercover", "168", "vestiaire"));
        }
//        for (Designer v : vs) {
//            System.out.println(v.getId() + " " + v.getName() + " " + v.getSlug() + " " + v.getCount());
//        }
        Designer grailed = new Designer();
        GrailedDesignerFetcher fetcher = new GrailedDesignerFetcher();
        String json = fetcher.fetch("https://www.grailed.com/api/designers");
        GrailedDesignerParser parser = new GrailedDesignerParser();
        List<Designer> gl = parser.parse(json);
        for (Designer d : gl) {
            d.setSource("grailed");
        }
        // Compare vs and gl
    }
}
