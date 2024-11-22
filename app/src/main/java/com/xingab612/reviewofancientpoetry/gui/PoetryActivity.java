package com.xingab612.reviewofancientpoetry.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.adapters.PoetryContentAdapter;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetry;
import com.xingab612.reviewofancientpoetry.managers.PoetryLayoutManager;
import com.xingab612.reviewofancientpoetry.misc.Data;

import java.util.ArrayList;
import java.util.StringJoiner;

public class PoetryActivity extends AppCompatActivity {
    private Data data;
    private int typeIndex;
    private int index;
    private AncientPoetry poetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry);

        init();
    }

    private void init() {
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

        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.poetry_toolbar);
        toolbar.setTitle(poetry.getTitle());

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView titleTextView = findViewById(R.id.poetry_poetry_name);
        titleTextView.setText(poetry.getTitle());
        TextView authorTextView = findViewById(R.id.poetry_poetry_author);
        authorTextView.setText(poetry.getDynastyAndAuthor());

        RecyclerView contentRecycler = findViewById(R.id.poetry_poetry_content);
        contentRecycler.setAdapter(new PoetryContentAdapter(this, poetry));
        contentRecycler.setLayoutManager(new PoetryLayoutManager());

        TextView appreciationTextView = findViewById(R.id.poetry_appreciation);
        if (poetry.getAppreciation() != null && !poetry.getAppreciation().isEmpty()) {
            appreciationTextView.setText(getString(R.string.appreciation, poetry.getAppreciation()));
        }
        TextView paraphraseTextView = findViewById(R.id.poetry_paraphrase);
        if (poetry.getParaphrase() != null && !poetry.getParaphrase().isEmpty()) {
            paraphraseTextView.setText(getString(R.string.paraphrase, poetry.getParaphrase()));
        }

        ArrayList<AncientPoetry.Comment> comments = poetry.getComments();
        TextView commentTextView = findViewById(R.id.poetry_comment);
        if (!comments.isEmpty()) {
            StringJoiner commentJoiner = new StringJoiner("\n");
            commentJoiner.add(getString(R.string.comments));
            for (AncientPoetry.Comment comment : poetry.getComments()) {
                commentJoiner.add(comment.toString(poetry));
            }
            commentTextView.setText(commentJoiner.toString());
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) commentTextView.getLayoutParams();
            params.setMargins(0, -15, 0, -15);
            commentTextView.setLayoutParams(params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}