package com.xingab612.reviewofancientpoetry.adapters;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;
import com.xingab612.reviewofancientpoetry.gui.MainActivity;
import com.xingab612.reviewofancientpoetry.gui.SecondActivity;
import com.xingab612.reviewofancientpoetry.misc.Data;
import com.xingab612.reviewofancientpoetry.util.DialogUtil;
import com.xingab612.reviewofancientpoetry.util.MenuUtil;

import java.util.ArrayList;

public class AncientPoetryTypeAdapter extends RecyclerView.Adapter<AncientPoetryTypeAdapter.AncientPoetryTypeHolder> {
    private final MainActivity activity;
    private final Data data;

    @NonNull
    @Override
    public AncientPoetryTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.type_list, null);
        return new AncientPoetryTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AncientPoetryTypeHolder holder, int position) {
        Button btn = holder.btn;
        btn.setText(data.readData().get(position).getName());
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(activity, SecondActivity.class);
            intent.putExtra("index", position);
            activity.startActivity(intent);
        });
        btn.setOnLongClickListener(v -> {
            PopupMenu popup = MenuUtil.showPopupMenu(activity, v, R.menu.menu_sim, activity.touchPoint);
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_sim_del) {
                    DialogUtil.showDeleteDialog(activity, data.readData().get(position).getName(), () -> {
                        data.readData().remove(position);
                        data.save();
                        notifyItemRemoved(position); // 通知 RecyclerView 移除该条目
                    });
                    return true;
                } else if (item.getItemId() == R.id.menu_sim_rename) {
                    DialogUtil.showRenameDialog(activity, text -> {
                        data.readData().get(position).setName(text);
                        data.save();
                        notifyItemChanged(position); // 通知 RecyclerView 更新该条目
                    }, data.readData().get(position).getName());
                    return true;
                }
                return false;
            });
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return data.readData().size();
    }

    public AncientPoetryTypeAdapter(MainActivity activity, Data data) {
        this.activity = activity;
        this.data = data;
    }

    public static class AncientPoetryTypeHolder extends RecyclerView.ViewHolder {
        Button btn;

        public AncientPoetryTypeHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.button_type_list);
        }
    }
}
