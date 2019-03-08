package com.android.lmc.tool;

import android.util.Log;

import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;

public class LogHelper {
    public static void init(){
        Logger.addLogAdapter(new DiskLogAdapter());
    }


    public static void d(String tag, String msg, boolean isDisk){
        if(isDisk){
            Logger.d(tag + "--" + msg);
        }else{
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg, boolean isDisk){
        if(isDisk){
            Logger.e(tag + "--" + msg);
        }else{
            Log.e(tag, msg);
        }
    }
}
