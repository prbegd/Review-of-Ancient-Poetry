package com.xingab612.reviewofancientpoetry.beans;

import org.intellij.lang.annotations.RegExp;

import java.io.Serial;
import java.io.Serializable;

public class Punctuation extends DisplayChar implements Serializable {
    @RegExp
    public static final String PUNCTUATION_REGEX = "[。！？，]";
    @Serial
    private static final long serialVersionUID = 7638218842901006706L;

    private Punctuation(char c) {
        super(c);
    }
    public static Punctuation of(char c) {
        if (!String.valueOf(c).matches(PUNCTUATION_REGEX)) {
            throw new IllegalArgumentException("Not a punctuation character: " + c);
        }
        return new Punctuation(c);
    }
}
