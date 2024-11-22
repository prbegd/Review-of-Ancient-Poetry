package com.xingab612.reviewofancientpoetry.beans;

import androidx.annotation.NonNull;

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

    private final ArrayList<Comment> comments = new ArrayList<>(); // 句子里的注释

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

    public void setPinyin(String pinyinStr) {
        String[] pinyinArr = pinyinStr.split(" ");
        ArrayList<Kanji> kanjiList = getKanjiList();
        if (kanjiList.size() != pinyinArr.length) {
            throw new IllegalArgumentException("The number of pinyin and words is not equal.(at poetry " + title + ", should be " + kanjiList.size() + ",but got " + pinyinArr.length + ")");
        }

        for (int i = 0; i < kanjiList.size(); i++) {
            String s = pinyinArr[i];
            if (s.equals("/")) {
                continue;
            }
            Kanji.Pinyin pinyin;
            try {
                pinyin = Kanji.Pinyin.of(s);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid pinyin in kanji " + (i + 1) + " (" + kanjiList.get(i).getChar() + ") of " + title + "!", e);
            }
            kanjiList.get(i).setPinyin(pinyin);
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

    public ArrayList<Kanji> getKanjiList() {
        ArrayList<Kanji> kanjiList = new ArrayList<>();
        for (Sentence sentence : sentences) {
            kanjiList.addAll(List.of(sentence.getWords()));
        }
        return kanjiList;
    }

    public Comment getCommentForKanji(int index) {
        for (Comment comment : comments) {
            if (comment.start <= index && comment.end > index) {
                return comment;
            }
        }
        return null;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * 注释
     */
    public record Comment(int start, int end, String content) implements Serializable {
        @Serial
        private static final long serialVersionUID = 52949743042551198L;

        /**
         * @param start   注释的起始位置索引
         * @param end     注释的结束位置索引
         * @param content 注释的内容
         */
        public Comment {
        }

        public String getString(AncientPoetry poetry) {
            StringBuilder sb = new StringBuilder();
            ArrayList<Kanji> kanjiList = poetry.getKanjiList();
            for (int i = start; i < end; i++) {
                sb.append(kanjiList.get(i).getChar());
            }
            return sb.toString();
        }

        @NonNull
        public String toString(AncientPoetry poetry) {
            return "[" + getString(poetry) + "] " + content;
        }

        @Override
        public String toString() {
            return "Comment[" +
                    "start=" + start + ", " +
                    "end=" + end + ", " +
                    "content=" + content + ']';
        }
    }
}
