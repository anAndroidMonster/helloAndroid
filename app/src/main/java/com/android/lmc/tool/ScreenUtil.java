package com.android.lmc.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class ScreenUtil {
    private static final String Tag = "ScreenUtil";

    public static int getWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getHeight(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getDpi(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    public static void logScreenInfo(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dpi = dm.densityDpi;
        Log.d(Tag, "屏幕信息：" + width + "*" + height + "-" + dpi);
    }
}
