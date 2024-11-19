package com.xingab612.reviewofancientpoetry.misc;

import androidx.annotation.NonNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 可更改的整数类。<br>
 * <strong>警告:线程不安全!</strong> 如果需要在多线程环境中使用, 请使用 {@link java.util.concurrent.atomic.AtomicInteger}。
 * </p>
 * <p>
 * 实例: 在方法中使用 {@link ModifiableInteger} 来记录变量的变化:<br>
 * <blockquote><pre>
 *     final ModifiableInteger count = new ModifiableInteger(0);
 *     for (int i = 0; i < 10; i++) {
 *         increment(count);
 *     }
 *
 *     public void increment(ModifiableInteger count) {
 *         count.set(count.get() + 1);
 *         System.out.println(count.get());
 *     }
 * </pre></blockquote>
 * </p>
 */
public final class ModifiableInteger implements Serializable, Modifiable<Integer>, Comparable<ModifiableInteger>, Cloneable {
    @Serial
    private static final long serialVersionUID = 71123913373943300L;
    private int value;

    public ModifiableInteger(int value) {
        this.value = value;
    }

    public static ModifiableInteger valueOf(int value) {
        return new ModifiableInteger(value);
    }

    public Integer get() {
        return value;
    }

    public void set(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(ModifiableInteger o) {
        return Integer.compare(value, o.value);
    }

    @NonNull
    @Override
    public ModifiableInteger clone() {
        try {
            ModifiableInteger clone = (ModifiableInteger) super.clone();
            clone.value = value;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
