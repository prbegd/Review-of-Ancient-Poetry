package com.xingab612.reviewofancientpoetry.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.hutool.core.io.IORuntimeException;

public class MenuUtil {
    private MenuUtil() {
    }

    /**
     * 在指定的位置显示PopupMenu
     * @param context 上下文, 一般为Activity
     * @param view 触发事件的View
     * @param menuRes 菜单资源ID
     * @param point 显示位置
     * @return PopupMenu对象
     */
    @NonNull
    public static PopupMenu showPopupMenu(Context context, View view, @MenuRes int menuRes, Point point) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        try {
            Field field = PopupMenu.class.getDeclaredField("mPopup");
            field.setAccessible(true);
            Method method = MenuPopupHelper.class.getDeclaredMethod("show", int.class, int.class);
            method.setAccessible(true);

            int[] relativePos = new int[2];
            view.getLocationOnScreen(relativePos);
            int x = point.x - relativePos[0];
            int y;
            float popupHeight = dpToPx(48 * popup.getMenu().size());
            if (getScreenHeight(context) - point.y >= popupHeight) {
                y = point.y - (relativePos[1] + view.getHeight());
            } else {
                y = point.y - relativePos[1];
            }
            method.invoke(field.get(popup), x, y);
        } catch (Exception e) {
            throw new RuntimeException("在显示PopupMenu时发生异常!!", e);
        }
        return popup;
    }
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
