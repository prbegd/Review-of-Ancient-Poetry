package com.xingab612.reviewofancientpoetry.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;

public class AncientPoetryAdapter extends BaseAdapter {
    private final Activity activity;
    private final AncientPoetryType type;

    public AncientPoetryAdapter(Activity activity, AncientPoetryType type) {
        this.activity = activity;
        this.type = type;
    }

    @Override
    public int getCount() {
        return type.getPoemList().size();
    }

    @Override
    public Object getItem(int position) {
        return type.getPoemList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(activity, R.layout.poetry_list, null);
        ImageView imageView = view.findViewById(R.id.poetry_list_bg);
        TextView titleTextView = view.findViewById(R.id.poetry_list_title);
        AncientPoetry poetry = type.getPoemList().get(position);

        if (poetry.getBackground()!= null) {
            imageView.setImageBitmap(poetry.getBackground());
        }
        titleTextView.setText(poetry.getTitle());
        return null;
    }
}
