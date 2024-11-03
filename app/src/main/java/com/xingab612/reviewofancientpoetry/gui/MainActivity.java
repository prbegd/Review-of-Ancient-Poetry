package com.xingab612.reviewofancientpoetry.gui;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.adapters.AncientPoetryTypeAdapter;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;
import com.xingab612.reviewofancientpoetry.misc.Data;
import com.xingab612.reviewofancientpoetry.util.DialogUtil;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public Point touchPoint = new Point();
    private Data data;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        data = new Data(this);

        recycler = findViewById(R.id.type_list);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        recycler.setAdapter(new AncientPoetryTypeAdapter(this, data));
    }

    public void onAboutClick(MenuItem item) {
        DialogUtil.showAboutDialog(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchPoint.x = (int) ev.getRawX();
            touchPoint.y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void onAddTypeButtonClicked(View view) {
        DialogUtil.showAddTypeDialog(this, text -> {
            data.readData().add(new AncientPoetryType(text));
            data.save();
            Objects.requireNonNull(recycler.getAdapter()).notifyItemInserted(data.readData().size() - 1);
        });
    }
}