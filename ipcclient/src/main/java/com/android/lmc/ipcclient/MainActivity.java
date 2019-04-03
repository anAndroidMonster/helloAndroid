package com.android.lmc.ipcclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.android.lmc.ipcservice.IMyAidl;

public class MainActivity extends AppCompatActivity {
    private IMyAidl mAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }

    private void initEvent(){
        findViewById(R.id.tv_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.android.lmc.aidl");
                intent.setPackage("com.android.lmc.ipcservice");
                bindService(intent, new ConnectCallBack(), Context.BIND_AUTO_CREATE);
            }
        });
    }

    class ConnectCallBack implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAidl = IMyAidl.Stub.asInterface(iBinder);
            doAidl();
        }

        // 服务断开连接回调
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mAidl = null;
        }
    }

    private void doAidl(){
        try {
            String result = mAidl.combine("hello", "world");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
