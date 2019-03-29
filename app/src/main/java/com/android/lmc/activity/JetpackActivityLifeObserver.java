package com.android.lmc.activity;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class JetpackActivityLifeObserver implements LifecycleObserver {
    private final String Tag = "lifeOb";
    private Callback mCallback;

    public JetpackActivityLifeObserver(Callback callback){
        mCallback = callback;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.d(Tag, "onCreate");
        mCallback.updateUi();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.d(Tag, "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.d(Tag, "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d(Tag, "onDestroy");
    }

    public interface Callback{
        void updateUi();
    }
}
