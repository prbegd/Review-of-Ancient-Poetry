package com.xingab612.reviewofancientpoetry.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.beans.DisplayChar;
import com.xingab612.reviewofancientpoetry.beans.Kanji;

public class PoetryContentAdapter extends RecyclerView.Adapter<PoetryContentAdapter.PoetryContentHolder> {
    private final AncientPoetry poetry;
    private final Activity activity;

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
        DisplayChar displayChar = poetry.getDisplayChars().get(position);
        holder.kanji.setText(String.valueOf(displayChar.getChar()));
        if (displayChar instanceof Kanji kanji) {
            Kanji.Pinyin pinyin = kanji.getPinyin();
            if (pinyin != null) {
                holder.pinyin.setText(pinyin.getString());
            }
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
