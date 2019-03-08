package com.android.lmc.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    private static ActivityCollector mInstance;
    private List<Activity> mActList = new ArrayList<>();

    public static ActivityCollector getInstance(){
        if(mInstance == null){
            synchronized (ActivityCollector.class){
                if(mInstance == null){
                    mInstance = new ActivityCollector();
                }
            }
        }
        return mInstance;
    }

    public void add(Activity act){
        if(!mActList.contains(act)) {
            mActList.add(act);
        }
    }

    public void remove(Activity act){
        if(mActList.contains(act)){
            mActList.remove(act);
        }
    }

    public void quitApp(){
        for(Activity act: mActList){
            act.finish();
        }
        mActList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
