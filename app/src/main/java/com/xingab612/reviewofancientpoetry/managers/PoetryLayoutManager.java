package com.xingab612.reviewofancientpoetry.managers;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xingab612.reviewofancientpoetry.R;
import com.xingab612.reviewofancientpoetry.beans.Punctuation;
import com.xingab612.reviewofancientpoetry.misc.ModifiableInteger;

public class PoetryLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        ModifiableInteger topOffset = ModifiableInteger.valueOf(0); // 初始顶部偏移
        ModifiableInteger leftOffset = ModifiableInteger.valueOf(0); // 初始左侧偏移

        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChild(view, 0, 0);
            int height = getDecoratedMeasuredHeight(view);
            int width = 120;

            // 布局视图
            layoutDecorated(view, leftOffset.get(), topOffset.get(), leftOffset.get() + width, topOffset.get() + height);
            leftOffset.set(leftOffset.get() + width); // 增加左侧偏移量以适应下一个视图

            if (view instanceof RelativeLayout layout) {
                handleRelativeLayout(layout, topOffset, height, leftOffset);
            }
        }
    }

    private void handleRelativeLayout(RelativeLayout layout, ModifiableInteger topOffset, int height, ModifiableInteger leftOffset) {
        for (int j = 0; j < layout.getChildCount(); j++) {
            View child = layout.getChildAt(j);

            if (child instanceof TextView text && text.getId() == R.id.poetry_content_kanji) {
                String textContent = text.getText().toString();

                if (textContent.matches(Punctuation.PUNCTUATION_REGEX)) {
                    // 在标点符号处换行
                    topOffset.set(topOffset.get() + height); // 增加偏移量以适应新的行
                    leftOffset.set(0); // 重置左侧偏移量
                    break; // 跳出循环，标点符号之后的子视图不再处理
                }
            }
        }
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }
}