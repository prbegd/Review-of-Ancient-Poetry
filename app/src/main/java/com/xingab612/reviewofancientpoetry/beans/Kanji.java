package com.xingab612.reviewofancientpoetry.beans;

import androidx.annotation.NonNull;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.io.Serial;
import java.io.Serializable;

import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.extra.pinyin.engine.pinyin4j.Pinyin4jEngine;

/**
 * 表示汉字的类
 */
public class Kanji extends DisplayChar implements Serializable {
    @Serial
    private static final long serialVersionUID = -8613873727409659953L;
    private Pinyin pinyin;

    private Kanji(char kanji, Pinyin pinyin) {
        super(kanji);
        this.pinyin = pinyin;
    }


    public static Kanji of(char kanji) {
        if (PinyinUtil.isChinese(kanji)) {
            return new Kanji(kanji, Pinyin.of(Pinyin.pinyinEngine.getPinyin(kanji)));
        } else {
            throw new IllegalArgumentException("Invalid kanji: " + kanji);
        }
    }
    public static Kanji of(char kanji, Pinyin pinyin) {
        if (PinyinUtil.isChinese(kanji)) {
            return new Kanji(kanji, pinyin);
        } else {
            throw new IllegalArgumentException("Invalid kanji: " + kanji);
        }
    }
    public static Kanji of(char kanji, String pinyin) {
        if (PinyinUtil.isChinese(kanji)) {
            return new Kanji(kanji, Pinyin.of(pinyin));
        } else {
            throw new IllegalArgumentException("Invalid kanji: " + kanji);
        }
    }

    /**
     * 获取
     *
     * @return pinyin
     */
    public Pinyin getPinyin() {
        return pinyin;
    }

    /**
     * 设置
     *
     * @param pinyin
     */
    public void setPinyin(Pinyin pinyin) {
        this.pinyin = pinyin;
    }

    @NonNull
    @Override
    public String toString() {
        return "Kanji{kanji = " + getChar() + ", pinyin = " + pinyin + "}";
    }
    public static class Pinyin implements Serializable {
        public static final Pinyin4jEngine pinyinEngine;
        static {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
            format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
            pinyinEngine = new Pinyin4jEngine(format);
        }
        @Serial
        private static final long serialVersionUID = 4631622513387336661L;
        private final String pinyin;
        private Pinyin(String pinyin) {
            this.pinyin = pinyin;
        }
        public static Pinyin of(String pinyin) {
            if (isLegal(pinyin)) {
                return new Pinyin(pinyin);
            } else {
                throw new IllegalArgumentException("Invalid pinyin: " + pinyin);
            }
        }
        public static boolean isLegal(String pinyin) {
            return pinyin.matches("[a-zāáăàaēéĕèeīíĭìiōóŏòoūúŭùuǖǘǚǜü]+");
        }

        public String getString() {
            return pinyin;
        }

        @NonNull
        @Override
        public String toString() {
            return "Pinyin{" +
                    "pinyin=" + pinyin +
                    '}';
        }
    }
}
