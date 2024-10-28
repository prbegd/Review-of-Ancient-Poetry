package com.xingab612.reviewofancientpoetry.gui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xingab612.reviewofancientpoetry.R;

public class MainActivity extends AppCompatActivity {
//    private final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAboutClick(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
    }
}