package com.android.lmc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.android.lmc.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiBuildActivity extends AppCompatActivity {

    @BindView(R.id.tv_channel) TextView mTvName;

    public static void enterActivity(Context context){
        Intent intent = new Intent(context, MultiBuildActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_multi_build);
        ButterKnife.bind(MultiBuildActivity.this);
        initData();
    }

    private void initData(){
        String name = "";
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Object channel = appInfo.metaData.get("CHANNEL_NAME");
            name = channel.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mTvName.setText(name);
    }
}
