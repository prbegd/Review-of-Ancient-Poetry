package com.xingab612.reviewofancientpoetry.beans;

import java.util.ArrayList;

/**
 * 代表一个类型的诗歌
 */
public class AncientPoetryType {
    private String name;
    private final ArrayList<AncientPoetry> poemList = new ArrayList<>();

    public AncientPoetryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AncientPoetry> getPoemList() {
        return poemList;
    }
}
