package org.example.grailedmldemo.parser;

import com.google.gson.*;
import org.example.grailedmldemo.model.GrailedDesigner;

import java.util.ArrayList;
import java.util.List;

public class GrailedDesignerParser implements Parser {

    Gson gson = new Gson();

    @Override
    public List<GrailedDesigner> parse(String json) {
        List<GrailedDesigner> grailedDesignerRaw = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray jsonList = jsonObject.getAsJsonArray("data");
        for (JsonElement jsonElement : jsonList) {
            grailedDesignerRaw.add(gson.fromJson(jsonElement, GrailedDesigner.class));
        }
        return grailedDesignerRaw;
    }
}
