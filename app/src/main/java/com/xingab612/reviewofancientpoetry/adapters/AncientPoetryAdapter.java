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

import java.util.ArrayList;
import java.util.function.Consumer;

public class AncientPoetryAdapter extends BaseAdapter {
    private final Activity activity;
//    private final AncientPoetryType type;
    private final ArrayList<AncientPoetry> poetryList;

    public AncientPoetryAdapter(Activity activity, AncientPoetryType type) {
        this.activity = activity;
//        this.type = type;
        this.poetryList = type.getPoetryList();
    }

    @Override
    public int getCount() {
        return poetryList.size();
    }

    @Override
    public Object getItem(int position) {
        return poetryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(activity, R.layout.poetry_list, null);
        TextView titleTextView = view.findViewById(R.id.poetry_list_title);
        AncientPoetry poetry = poetryList.get(position);

        titleTextView.setText(poetry.getTitle());
        return view;
    }
}
