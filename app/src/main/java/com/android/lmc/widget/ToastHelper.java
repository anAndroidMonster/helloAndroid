package com.android.lmc.widget;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lmc.R;
import com.android.lmc.tool.ScreenUtil;
import com.android.lmc.tool.StringUtil;

/**
 * Created by limin on 2017/6/12.
 */

public class ToastHelper {
    private static ToastHelper mInstance;
    private static Context mAppContext;

    public static ToastHelper getInstance(){
        if(mInstance == null){
            synchronized (ToastHelper.class){
                mInstance = new ToastHelper();
            }
        }
        return mInstance;
    }

    public void init(Application appContext){
        mAppContext = appContext;
    }

    public static void show(String message) {
        if(StringUtil.isEmpty(message) || mAppContext == null) return;
        //加载Toast布局
        View toastRoot = LayoutInflater.from(mAppContext).inflate(R.layout.view_toast, null);
        //初始化布局控件
        TextView mTextView = (TextView) toastRoot.findViewById(R.id.tv_content);
        mTextView.setText(message);
        ViewGroup.LayoutParams lp = mTextView.getLayoutParams();
        lp.width = ScreenUtil.getWidth(mAppContext);
        mTextView.setLayoutParams(lp);
        //Toast的初始化
        Toast toastStart = new Toast(mAppContext);
        toastStart.setGravity(Gravity.TOP, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();

    }
}
