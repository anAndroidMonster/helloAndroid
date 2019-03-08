package com.android.lmc.common;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.android.lmc.MainActivity;
import com.android.lmc.widget.ToastHelper;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefHandler;
    private static CrashHandler mInstance;

    public static CrashHandler getInstance(){
        if(mInstance == null){
            synchronized (CrashHandler.class){
                if(mInstance == null){
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    private CrashHandler(){

    }

    public void init(Application context){
        mContext = context;
        mDefHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(!handlerException(e) && mDefHandler != null){
            mDefHandler.uncaughtException(t, e);
        }else{
            try{
                Thread.sleep(2000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            relaunchApp();
            ActivityCollector.getInstance().quitApp();

        }
    }

    private boolean handlerException(Throwable e){
        if (e == null){
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastHelper.show("很抱歉,程序出现异常,即将重启");
                Looper.loop();
            }
        }.start();
        //todo 打印日志
        return true;
    }

    private void relaunchApp(){
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent restartIntent = PendingIntent.getActivity(
                mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500, restartIntent); // 0.5秒
    }

}
