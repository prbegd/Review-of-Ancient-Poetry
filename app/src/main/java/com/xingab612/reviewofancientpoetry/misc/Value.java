package com.xingab612.reviewofancientpoetry.misc;

public class Value<T> {
    private T value;
    public Value(T value) {
        this.value = value;
    }
    public Value() {}

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
