package com.xingab612.reviewofancientpoetry.beans;

import android.graphics.Bitmap;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 一首诗词
 */
public class AncientPoetry implements Serializable {
    @Serial
    private static final long serialVersionUID = -147997276505440314L;
    private String title = "标题";
    private String dynasty = "朝代";
    private String author = "作者";
    private final ArrayList<Sentence> sentences = new ArrayList<>();
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
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : sentences) {
            sb.append(sentence.getParaphrase());
        }
        return sb.toString();
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public AncientPoetry(String title) {
        this.title = title;
    }
}
