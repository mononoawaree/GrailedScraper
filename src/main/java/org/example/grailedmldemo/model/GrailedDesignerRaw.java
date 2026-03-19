package org.example.grailedmldemo.model;

import lombok.Data;

@Data
public class GrailedDesignerRaw {

    private int id;
    private String name;
    private int count;

    public GrailedDesignerRaw(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
