package com.xingab612.reviewofancientpoetry.beans;

import java.util.ArrayList;

/**
 * 一首诗词
 */
public class AncientPoetry {
    private String title = "标题";
    private String dynasty = "朝代";
    private String author = "作者";
    private final ArrayList<Sentence> sentences = new ArrayList<>();
    private String paraphrase; // 释义
    private String appreciation; // 赏析

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }
}
