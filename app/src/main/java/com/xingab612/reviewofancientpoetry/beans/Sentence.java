package com.xingab612.reviewofancientpoetry.beans;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 一个句子,包含标点符号
 */
public class Sentence implements Serializable {
    @Serial
    private static final long serialVersionUID = -7756436494992819624L;
    private Kanji[] words; // 句子里的每个字
    private char punctuation; // 句子里的标点符号
    private String paraphrase; // 句子的意思
    private final ArrayList<Comment> comments = new ArrayList<>(); // 句子里的注释

    /**
     * 从字符串中创建一个句子对象。
     *
     * @param sentence 待解析的句子字符串，仅支持包含中文字符和常用标点符号（句号、问号、感叹号、逗号）的字符串。
     * @return 根据输入的句子字符串创建的Sentence对象。
     * @throws IllegalArgumentException 如果输入的句子字符串不符合要求（即不是仅由中文字符和常用标点符号组成），则抛出此异常。
     */
    public static Sentence fromString(String sentence) {
        if (!sentence.matches("[一-龥]+[。！？，]")) {
            throw new IllegalArgumentException("非法句子: " + sentence);
        }
        Kanji[] words = new Kanji[sentence.length()];
        for (int i = 0; i < sentence.length() - 1; i++) {
            words[i] = new Kanji(sentence.charAt(i));
        }
        char punctuation = sentence.charAt(sentence.length() - 1);
        return new Sentence(words, punctuation);
    }

    /**
     * 句子里的注释
     *
     * @param start   注释的起始位置索引
     * @param end     注释的结束位置索引
     * @param content 注释的内容
     */
    public record Comment(int start, int end, String content) {
    }

    public Sentence(Kanji[] words, char punctuation) {
        this.words = words;
        this.punctuation = punctuation;
    }

    public Kanji[] getWords() {
        return words;
    }

    public char getPunctuation() {
        return punctuation;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setWords(Kanji[] words) {
        this.words = words;
    }

    public void setPunctuation(char punctuation) {
        this.punctuation = punctuation;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }
}
