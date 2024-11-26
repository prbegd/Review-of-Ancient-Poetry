package com.xingab612.reviewofancientpoetry.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.misc.Data;
import com.xingab612.reviewofancientpoetry.util.DialogUtil;

public class EditActivity extends AppCompatActivity {
    private int typeIndex;
    private int index;
    private AncientPoetry poetry;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        data = new Data(this);

        Intent intent = getIntent();
        typeIndex = intent.getIntExtra("type", -1);
        index = intent.getIntExtra("index", -1);

        if (typeIndex == -1 || index == -1) {
            Toast.makeText(this, R.string.load_failed, Toast.LENGTH_SHORT).show();
            finish();
            return; // 提前返回，避免后续代码执行
        }

        poetry = data.readData().get(typeIndex).getPoetryList().get(index);
        if (poetry == null) { // 添加错误处理机制
            Toast.makeText(this, R.string.load_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.edit_poetry_toolbar);
        toolbar.setTitle(poetry.getTitle());

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView titleEdit = findViewById(R.id.edit_poetry_name);
        titleEdit.setText(poetry.getTitle());
        titleEdit.setOnClickListener(v -> {
            DialogUtil.showEditPoetryDialog(this, s -> {
                poetry.setTitle(s);
                data.save();
                this.recreate();
            }, poetry.getTitle());
        });
        TextView authorEdit = findViewById(R.id.edit_poetry_author);
        authorEdit.setText(poetry.getDynastyAndAuthor());
        authorEdit.setOnClickListener(v ->
                DialogUtil.showEditAuthorDialog(this, (dynasty, author) -> {
            poetry.setDynasty(dynasty.isEmpty() ? null : dynasty);
            poetry.setAuthor(author);
            data.save();
            this.recreate();
        }, poetry.getDynasty(), poetry.getAuthor()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }
}