package com.android.lmc.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

import com.android.lmc.R;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadActivity extends AppCompatActivity {
    @BindView(R.id.tv_result)
    TextView mTvResult;

    private static MyHandler mHandler;
    private static class MyHandler extends Handler{
        private final WeakReference<ThreadActivity> mActivity;
        public MyHandler(ThreadActivity activity) {
            mActivity = new WeakReference<ThreadActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ThreadActivity activity = mActivity.get();
            if (activity != null) {
                if(msg == null || msg.obj == null) return;
                String content = msg.obj.toString();
                activity.showResult(content);
            }
        }
    }

    private HandlerThread myHandlerThread;
    private Handler myHandlerThreadHandler;

    public static void enterActivity(Context context){
        Intent intent = new Intent(context, ThreadActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(ThreadActivity.this);
        mHandler = new MyHandler(ThreadActivity.this);
    }

    @Override
    public void onDestroy(){
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if(myHandlerThreadHandler != null){
            myHandlerThreadHandler.removeCallbacksAndMessages(null);
            myHandlerThreadHandler = null;
        }
        if(myHandlerThread != null){
            myHandlerThread.quit();
            myHandlerThread = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.tv_thread)
    public void onThread(){
        new MyThread().start();
    }

    public static class MyThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = Message.obtain();
            msg.obj = "thread";
            mHandler.sendMessage(msg);
        }
    }

    public void showResult(String txt){
        mTvResult.setText(txt);
    }

    @OnClick(R.id.tv_handler_thread)
    public void onHandlerThread(){
        if(myHandlerThread == null) {
            myHandlerThread = new HandlerThread("HandlerThread");
            myHandlerThread.start();

            myHandlerThreadHandler = new Handler(myHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message uiMsg = Message.obtain();
                    uiMsg.obj = "handlerThread";
                    mHandler.sendMessage(uiMsg);
                }
            };
        }

        myHandlerThreadHandler.sendEmptyMessage(0);
    }

    @OnClick(R.id.tv_async_query_handler)
    public void onAsyncQueryHandler(){
        QueryHandler handler = new QueryHandler(getContentResolver(), ThreadActivity.this);
        handler.startQuery(0, "async_query_handler", null, null, null, null, null);
    }

    private static class QueryHandler extends AsyncQueryHandler {
        private final WeakReference<ThreadActivity> mActivity;
        public QueryHandler(ContentResolver cr, ThreadActivity activity) {
            super(cr);
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            ThreadActivity activity = mActivity.get();
            if (activity != null) {
                activity.showResult(cookie.toString());
            }

        }

    }

    private MyLoader mLoader;
    @OnClick(R.id.tv_loader)
    public void onLoader(){
        if(mLoader == null) {
            mLoader = new MyLoader(ThreadActivity.this);
            LoaderManager.getInstance(ThreadActivity.this).initLoader(0, null, mCallback).forceLoad();
        }
//        LoaderManager.getInstance(ThreadActivity.this).restartLoader(1000, null, mCallback);
    }

    LoaderManager.LoaderCallbacks<String> mCallback = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new MyLoader(ThreadActivity.this);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            showResult(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {
            //停止使用结果
        }
    };

    private static class MyLoader extends AsyncTaskLoader<String>{
        public MyLoader(Context context){
            super(context);
        }

        @Nullable
        @Override
        public String loadInBackground() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "loader";
        }
    }
}
