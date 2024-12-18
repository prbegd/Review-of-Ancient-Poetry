package com.xingab612.reviewofancientpoetry.gui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.adapters.AncientPoetryAdapter;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;
import com.xingab612.reviewofancientpoetry.misc.Data;
import com.xingab612.reviewofancientpoetry.util.DialogUtil;
import com.xingab612.reviewofancientpoetry.util.MenuUtil;

import java.util.ArrayList;
import java.util.Objects;


public class SecondActivity extends AppCompatActivity {
    public Point touchPoint = new Point();
    private Data data;
    private int index;
    private ListView list;
    private ArrayList<AncientPoetry> poetryList;
    private AncientPoetryAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                this.recreate();
            }
        });

        init();
    }
    private void init() {
        data = new Data(this);

        index = getIntent().getIntExtra("index", -1);
        if (index == -1) {
            Toast.makeText(this, R.string.load_failed, Toast.LENGTH_SHORT).show();
            finish();
            return; // 提前返回，避免后续代码执行
        }

        AncientPoetryType type = data.readData().get(index);
        if (type == null) { // 添加错误处理机制
            Toast.makeText(this, R.string.load_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.second_toolbar);
        toolbar.setTitle(type.getName());

        poetryList = type.getPoetryList();
        list = findViewById(R.id.poetry_list);
        adapter = new AncientPoetryAdapter(this, type); // 简化保存操作
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, PoetryActivity.class);
            intent.putExtra("type", index);
            intent.putExtra("index", position);
            launcher.launch(intent);
        });

        list.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position < 0 || position >= poetryList.size()) {
                return false; // 处理越界情况
            }

            PopupMenu popup = MenuUtil.showPopupMenu(this, view, R.menu.menu_sim, touchPoint);
            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_sim_del) {
                    DialogUtil.showDeleteDialog(this, poetryList.get(position).getTitle(), () -> {
                        poetryList.remove(position);
                        data.save();
                        adapter.notifyDataSetChanged();
                    });
                    return true;
                } else if (itemId == R.id.menu_sim_rename) {
                    DialogUtil.showRenameDialog(this, text -> {
                        poetryList.get(position).setTitle(text);
                        data.save();
                        adapter.notifyDataSetChanged();
                    }, poetryList.get(position).getTitle());
                    return true;
                }
                return false;
            });
            return true;
        });

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void onAddPoetryButtonClicked(View view) {

        DialogUtil.showAddPoetryDialog( this, text -> {
            poetryList.add(new AncientPoetry(text));
            data.save();
            ((AncientPoetryAdapter) Objects.requireNonNull(list.getAdapter())).notifyDataSetChanged();
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchPoint.x = (int) ev.getRawX();
            touchPoint.y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

}