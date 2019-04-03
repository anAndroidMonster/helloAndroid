package com.android.lmc.ipcservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static {
        System.loadLibrary("hello");
    }

    public native String sayHello();

    @Override
    public void onResume(){
        super.onResume();
        Toast.makeText(MainActivity.this, sayHello(), Toast.LENGTH_SHORT).show();
    }
}
