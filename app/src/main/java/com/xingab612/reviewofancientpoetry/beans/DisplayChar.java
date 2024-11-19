package com.xingab612.reviewofancientpoetry.beans;

import java.io.Serial;
import java.io.Serializable;

public abstract class DisplayChar implements Serializable {
    @Serial
    private static final long serialVersionUID = 7554611564990024975L;
    private char c;
    public DisplayChar(char c) {
        this.c = c;
    }

    public char getChar() {
        return c;
    }

    public void setChar(char c) {
        this.c = c;
    }
}
