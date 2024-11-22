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
import com.xujiaji.happybubble.BubbleDialog;

public class PoetryContentAdapter extends RecyclerView.Adapter<PoetryContentAdapter.PoetryContentHolder> {
    private final AncientPoetry poetry;
    private final Activity activity;
    private int kanjiIndex = 0;

    public PoetryContentAdapter(Activity activity, AncientPoetry poetry) {
        this.poetry = poetry;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PoetryContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.poetry_content, null);
        return new PoetryContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryContentHolder holder, int position) {
        Log.d("PoetryContentAdapter", "onBindViewHolder: " + position);
        DisplayChar displayChar = poetry.getDisplayChars().get(position);
        holder.kanji.setText(String.valueOf(displayChar.getChar()));
        if (displayChar instanceof Kanji kanji) {
            Kanji.Pinyin pinyin = kanji.getPinyin();
            if (pinyin != null) {
                holder.pinyin.setText(pinyin.getString());
            }

            AncientPoetry.Comment comment = poetry.getCommentForKanji(kanjiIndex);
            if (comment != null) {
                holder.kanji.setOnClickListener(v -> {
                    View bubbleContent = LayoutInflater.from(activity).inflate(R.layout.comment_bubble, null);
                    ((TextView) bubbleContent.findViewById(R.id.comment_text)).setText(comment.content());
                    BubbleDialog bubble = new BubbleDialog(activity);
                    bubble.setBubbleContentView(bubbleContent);
                    bubble.setClickedView(v);
                    bubble.setTransParentBackground();
                    bubble.show();
                });
                TextPaint paint = holder.kanji.getPaint();
                paint.setColor(activity.getColor(R.color.blue));
                paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
            kanjiIndex++;
        }
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
