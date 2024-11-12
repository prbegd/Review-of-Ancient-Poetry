package com.xingab612.reviewofancientpoetry.util;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.xingab612.reviewofancientpoetry.R;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DialogUtil {
    /**
     * 显示重命名对话框
     * @param c 上下文, 一般为Activity
     * @param listener 输入完成并点击确定按钮后的回调函数(输入为空时不调用)
     * @param oldName 旧名称, 当做默认输入内容
     */
    public static void showRenameDialog(Context c, Consumer<String> listener, String oldName) {
        EditText editText = new EditText(c);
        editText.setText(oldName);
        new AlertDialog.Builder(c)
                .setTitle(R.string.rename)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    String inputText = editText.getText().toString().trim();
                    if (!inputText.isEmpty()) {
                        listener.accept(inputText);
                    } else {
                        Toast.makeText(c, R.string.error_empty_content, Toast.LENGTH_SHORT).show(); // 添加错误提示
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setView(editText).create().show();
    }

    // 显示关于对话框
    public static void showAboutDialog(Context c) {
        new AlertDialog.Builder(c).setTitle(R.string.menu_main_about).setMessage(R.string.about_content).setPositiveButton(R.string.ok, null).setNegativeButton(R.string.copy, (dialog, witch) -> {
            ClipboardManager clipboard = (ClipboardManager) c.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(c.getString(R.string.inetaddress), "https://github.com/prbegd/Review-of-Ancient-Poetry");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(c, R.string.copied, Toast.LENGTH_SHORT).show();
        }).create().show();
    }

    /**
     * 显示删除对话框
     * @param c 上下文, 一般为Activity
     * @param toDelete 要删除的对象名称, 可以为null
     * @param listener 删除成功或失败后的回调函数
     */
    public static void showDeleteDialog(Context c, String toDelete, Runnable listener) {
        String message = toDelete == null ? c.getString(R.string.delete_tip1) : c.getString(R.string.delete_tip, toDelete);
        new AlertDialog.Builder(c).setTitle(R.string.delete).setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    listener.run();
                })
                .setNegativeButton(R.string.cancel, null)
                .create().show();
    }

    public static void showAddTypeDialog(Context c, Consumer<String> listener) {
        EditText editText = new EditText(c);
        new AlertDialog.Builder(c).setTitle(R.string.add_type)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    String inputText = editText.getText().toString().trim();
                    if (!inputText.isEmpty()) {
                        listener.accept(inputText);
                    } else {
                        Toast.makeText(c, R.string.error_empty_content, Toast.LENGTH_SHORT).show(); // 添加错误提示
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setView(editText).create().show();
    }
    public static void showAddPoetryDialog(Context c, Consumer<String> listener) {
        EditText editText = new EditText(c);
        new AlertDialog.Builder(c).setTitle(R.string.add_poetry)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    String inputText = editText.getText().toString().trim();
                    if (!inputText.isEmpty()) {
                        listener.accept(inputText);
                    } else {
                        Toast.makeText(c, R.string.error_empty_content, Toast.LENGTH_SHORT).show(); // 添加错误提示
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setView(editText).create().show();
    }
}