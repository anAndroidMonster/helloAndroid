package com.android.lmc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.lmc.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrashActivity extends AppCompatActivity {
    @BindView(R.id.tv_time)
    TextView mTvTime;

    public static void enterActivity(Context context){
        Intent intent = new Intent(context, CrashActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(CrashActivity.this);
        initData();
    }

    @OnClick(R.id.tv_activity)
    void onNewActivity(){
        CrashActivity.enterActivity(CrashActivity.this);
    }

    @OnClick(R.id.tv_crash)
    void onCrash(){
        int a = Integer.parseInt("aaa");
    }

    private void initData(){
        mTvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
