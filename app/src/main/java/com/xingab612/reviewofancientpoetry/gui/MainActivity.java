package com.xingab612.reviewofancientpoetry.gui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.util.AncientPoetryTypeAdapter;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder aboutDialog;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.inetaddress), "https://github.com/prbegd/Review-of-Ancient-Poetry");
        aboutDialog = new AlertDialog.Builder(this).setTitle(R.string.menu_main_about).setMessage(R.string.about_content).setPositiveButton(R.string.ok, null).setNegativeButton(R.string.copy, (dialog, witch) -> {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
        });

        recycler = findViewById(R.id.type_list);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        recycler.setAdapter(new AncientPoetryTypeAdapter(this));
    }

    public void onAboutClick(MenuItem item) {
        aboutDialog.create().show();
    }
}