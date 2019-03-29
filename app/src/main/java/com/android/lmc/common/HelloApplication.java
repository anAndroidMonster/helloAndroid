package com.android.lmc.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.lmc.service.OtherProgressService;
import com.android.lmc.tool.LogHelper;
import com.android.lmc.widget.ToastHelper;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

public class HelloApplication extends Application {

    private static HelloApplication mInstance;
    private final String Tag = "HelloApp";

    public static HelloApplication getInstance(){
        if(mInstance == null){
            synchronized (HelloApplication.class){
                if(mInstance == null){
                    mInstance = new HelloApplication();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        if(shouldInit()) {
            mInstance = this;
//            CrashHandler.getInstance().init(this);
            startService(new Intent(HelloApplication.this, OtherProgressService.class));
            watchActivity();
            ToastHelper.getInstance().init(this);
            LogHelper.init();
            if (!LeakCanary.isInAnalyzerProcess(this)) {
                LeakCanary.install(this);
            }
        }
    }

    private boolean shouldInit(){
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();//主进程包名
        int myPid = android.os.Process.myPid();//当前进程id
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            //当前进程为主进程
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private void watchActivity(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityCollector.getInstance().add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityCollector.getInstance().remove(activity);
            }
        });
    }
}
