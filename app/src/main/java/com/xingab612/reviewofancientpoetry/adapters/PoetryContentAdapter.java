package com.xingab612.reviewofancientpoetry.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.beans.DisplayChar;
import com.xingab612.reviewofancientpoetry.beans.Kanji;
import com.xingab612.reviewofancientpoetry.beans.Punctuation;
import com.xujiaji.happybubble.BubbleDialog;

import java.util.ArrayList;

public class PoetryContentAdapter extends RecyclerView.Adapter<PoetryContentAdapter.PoetryContentHolder> {
    private final AncientPoetry poetry;
    private final Activity activity;
    private final ArrayList<PoetryContentHolder> holders = new ArrayList<>();

    public PoetryContentAdapter(Activity activity, AncientPoetry poetry) {
        this.poetry = poetry;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PoetryContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.poetry_content, null);
        PoetryContentHolder holder = new PoetryContentHolder(view);
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryContentHolder holder, int position) {
        DisplayChar displayChar = poetry.getDisplayChars().get(position);
        holder.kanji.setText(String.valueOf(displayChar.getChar()));
        if (displayChar instanceof Kanji kanji) {
            Kanji.Pinyin pinyin = kanji.getPinyin();
            if (pinyin != null) {
                holder.pinyin.setText(pinyin.getString());
            }
        }
        if (position == poetry.getDisplayChars().size() - 1) { // 因为确定了Manager的操作才选择这种方式
            onViewHoldersBound();
        }
    }

    private void onViewHoldersBound() {
        for (int i = 0, j =0; i < holders.size(); i++, j++) {
            if (poetry.getDisplayChars().get(i) instanceof Punctuation) {
                holders.remove(j);
                j--;// 因为移除了，所以索引要减一
            }
        }
        for (int i = 0; i < holders.size(); i++) {
            PoetryContentHolder holder = holders.get(i);
            AncientPoetry.Comment comment = poetry.getCommentForKanji(i);
            Log.d("PoetryContentAdapter", "comment: " + comment);
            if (comment != null) {
                holder.kanji.setOnClickListener(v -> {
                    for (int j = comment.start(); j < comment.end(); j++) {
                        holders.get(j).kanji.setBackgroundColor(activity.getColor(R.color.blue));
                    }
                    View bubbleContent = LayoutInflater.from(activity).inflate(R.layout.comment_bubble, null);
                    ((TextView) bubbleContent.findViewById(R.id.comment_text)).setText(comment.content());
                    BubbleDialog bubble = getBubbleDialog(v, bubbleContent, comment);
                    bubble.show();
                });
                TextPaint paint = holder.kanji.getPaint();
                paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
        }
    }

    private @NonNull BubbleDialog getBubbleDialog(View v, View bubbleContent, AncientPoetry.Comment comment) {
        BubbleDialog bubble = new BubbleDialog(activity);
        bubble.setBubbleContentView(bubbleContent);
        bubble.setClickedView(v);
        bubble.setTransParentBackground();
        bubble.setOnDismissListener(d -> {
            for (int i = comment.start(); i < comment.end(); i++) {
                holders.get(i).kanji.setBackgroundColor(activity.getColor(R.color.transparent));
            }
        });
        return bubble;
    }

    @Override
    public int getItemCount() {
        return poetry.getDisplayChars().size();
    }

    public static class PoetryContentHolder extends RecyclerView.ViewHolder {
        TextView pinyin;
        TextView kanji;

        public PoetryContentHolder(@NonNull View itemView) {
            super(itemView);
            pinyin = itemView.findViewById(R.id.poetry_content_pinyin);
            kanji = itemView.findViewById(R.id.poetry_content_kanji);
        }
    }
}
