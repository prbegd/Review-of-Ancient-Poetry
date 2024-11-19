package com.xingab612.reviewofancientpoetry.beans;

import android.util.Log;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一首诗词
 */
public class AncientPoetry implements Serializable {
    @Serial
    private static final long serialVersionUID = -147997276505440314L;
    private String title;
    private String dynasty;
    private String author;
    private final ArrayList<Sentence> sentences = new ArrayList<>();
    private String appreciation; // 赏析
    private String paraphrase; // 译文

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

    public AncientPoetry(String title) {
        this.title = title;
    }

    public String getDynastyAndAuthor() {
        return (dynasty == null ? "" : "[" + dynasty + "] ") + (author == null ? "" : author);
    }
    public void setSentencesFromString(String sentencesStr) {
        if (sentencesStr == null || sentencesStr.isEmpty()) {
            throw new IllegalArgumentException("The sentences string is empty.");
        }
        String[] sentenceArr = sentencesStr.split("(?<=" + Punctuation.PUNCTUATION_REGEX + ")");
        for (String sentenceStr : sentenceArr) {
            sentences.add(Sentence.fromString(sentenceStr.trim()));
        }
    }
    public void setPinyin(String pinyin) {
        String[] pinyinArr = pinyin.split(" ");
        ArrayList<Kanji> kanjiList = new ArrayList<>();
        for (Sentence sentence : sentences) {
            kanjiList.addAll(List.of(sentence.getWords()));
        }
        if (kanjiList.size() != pinyinArr.length) {
            throw new IllegalArgumentException("The number of pinyin and words is not equal.");
        }

        for (int i = 0; i < kanjiList.size(); i++) {
            kanjiList.get(i).setPinyin(Kanji.Pinyin.of(pinyinArr[i]));
        }
    }

    public ArrayList<DisplayChar> getDisplayChars() {
        ArrayList<DisplayChar> displayChars = new ArrayList<>();
        for (Sentence sentence : sentences) {
            displayChars.addAll(Arrays.asList(sentence.getWords()));
            DisplayChar punctuation = sentence.getPunctuation();
            if (punctuation != null) {
                displayChars.add(punctuation); // 仅在标点不为null时添加
            }
        }
        return displayChars;
    }
}
