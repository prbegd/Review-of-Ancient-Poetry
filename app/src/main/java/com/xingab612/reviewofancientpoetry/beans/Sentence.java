package com.xingab612.reviewofancientpoetry.beans;

import android.util.Log;

import org.intellij.lang.annotations.RegExp;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 一个句子,包含标点符号
 */
public class Sentence implements Serializable {
    @Serial
    private static final long serialVersionUID = -7756436494992819624L;
    private Kanji[] words; // 句子里的每个字
    private Punctuation punctuation; // 句子里的标点符号
    @RegExp
    public static final String SENTENCE_REGEX = "[一-龥]+" + Punctuation.PUNCTUATION_REGEX;

    /**
     * 从字符串中创建一个句子对象。
     *
     * @param sentence 待解析的句子字符串，仅支持包含中文字符和常用标点符号（句号、问号、感叹号、逗号）的字符串。
     * @return 根据输入的句子字符串创建的Sentence对象。
     * @throws IllegalArgumentException 如果输入的句子字符串不符合要求（即不是仅由中文字符和常用标点符号组成），则抛出此异常。
     */
    public static Sentence fromString(String sentence) {
        if (!sentence.matches(SENTENCE_REGEX)) {
            throw new IllegalArgumentException("非法句子: " + sentence);
        }
        Kanji[] words = new Kanji[sentence.length() - 1];
        for (int i = 0; i < words.length; i++) {
            words[i] = Kanji.of(sentence.charAt(i));
        }
        char punctuation = sentence.charAt(sentence.length() - 1);
        return new Sentence(words, Punctuation.of(punctuation));
    }



    private Sentence(Kanji[] words, Punctuation punctuation) {
        this.words = words;
        this.punctuation = punctuation;
    }

    public Kanji[] getWords() {
        return words;
    }

    public Punctuation getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Punctuation punctuation) {
        this.punctuation = punctuation;
    }

    public void setWords(Kanji[] words) {
        this.words = words;
    }

//    public String getString() {
//        StringBuilder sb = new StringBuilder();
//        for (Kanji word : words) {
//            sb.append(word.getChar());
//        }
//        sb.append(punctuation);
//        return sb.toString();
//    }
}
