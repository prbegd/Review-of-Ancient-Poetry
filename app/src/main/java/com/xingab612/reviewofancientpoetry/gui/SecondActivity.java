package com.xingab612.reviewofancientpoetry.gui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;
import com.xingab612.reviewofancientpoetry.misc.Data;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        init();
    }
    private void init() {
        int value = getIntent().getIntExtra("index", -1);
        if (value == -1) {
            Toast.makeText(this, R.string.load_failed, Toast.LENGTH_SHORT).show();
            finish();
        }

        AncientPoetryType type = new Data(this).readData().get(value);
        Toolbar toolbar = findViewById(R.id.second_toolbar);
        toolbar.setTitle(type.getName());
    }
}