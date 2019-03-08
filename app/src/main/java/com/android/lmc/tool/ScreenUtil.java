package com.android.lmc.tool;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
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
        LogHelper.d(Tag, "屏幕信息：" + width + "*" + height + "-" + dpi, false);
    }

    public static void setFullScreen(boolean isFull){
        if(RootHelper.isRoot()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                setFullHigh(isFull);
            }else {
                setFullLow(isFull);
            }
        }
    }

    private static void setFullLow(boolean isFull){
        if (isFull) {
            RootHelper.getInstance().doCmd("service call activity 42 s16 com.android.systemui");
        } else {
            RootHelper.getInstance().doCmd("am startservice -n com.android.systemui/.SystemUIService");
        }
    }

    private static void setFullHigh(boolean isFull){
        Handler mHandler = new Handler();
        if(isFull){
            RootHelper.getInstance().doCmd("pm disable com.android.systemui");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RootHelper.getInstance().doCmd("am stopservice  -n com.android.systemui/.SystemUIService");
                }
            }, 1000*2);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String result = RootHelper.getInstance().doCmd("ps |grep com.android.systemui");
                    String[] resultArray = result.replaceAll(" {2,}", " ").split(" ");
                    if(resultArray.length > 2){
                        RootHelper.getInstance().doCmd("kill -9 " + resultArray[1]);
                    }
                }
            }, 1000*4);
        }else{
            RootHelper.getInstance().doCmd("pm enable com.android.systemui");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RootHelper.getInstance().doCmd("am startservice com.android.systemui/.SystemUIService");
                }
            }, 1000*2);

        }
    }
}
