package com.xingab612.reviewofancientpoetry.beans;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 代表一个类型的诗歌
 */
public class AncientPoetryType implements Serializable {
    @Serial
    private static final long serialVersionUID = 3944136703050029135L;
    private String name;
    private final ArrayList<AncientPoetry> poetryList = new ArrayList<>();

    public AncientPoetryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AncientPoetry> getPoetryList() {
        return poetryList;
    }
}
