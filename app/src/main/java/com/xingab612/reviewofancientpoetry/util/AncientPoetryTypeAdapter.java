package com.xingab612.reviewofancientpoetry.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;

import java.util.ArrayList;

public class AncientPoetryTypeAdapter extends RecyclerView.Adapter<AncientPoetryTypeAdapter.AncientPoetryTypeHolder> {
    private final ArrayList<AncientPoetryType> items;
    private final Activity activity;

    @NonNull
    @Override
    public AncientPoetryTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.type_list, null);
        return new AncientPoetryTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AncientPoetryTypeHolder holder, int position) {
        holder.btn.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public AncientPoetryTypeAdapter(ArrayList<AncientPoetryType> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public AncientPoetryTypeAdapter(Activity activity) {
        this.activity = activity;
        this.items = new ArrayList<>();
        items.add(new AncientPoetryType("全部"));
        items.add(new AncientPoetryType("古诗"));
        items.add(new AncientPoetryType("词赋"));
        for (int i = 0; i < 6; i++) {
            String grade = ChineseNumberUtil.convert(i + 1);
            items.add(new AncientPoetryType(grade + "年级上册"));
            items.add(new AncientPoetryType(grade + "年级下册"));
        }
    }

    public static class AncientPoetryTypeHolder extends RecyclerView.ViewHolder {
        Button btn;

        public AncientPoetryTypeHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.button_type_list);
        }
    }
}
