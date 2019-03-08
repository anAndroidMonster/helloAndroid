package com.android.lmc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.lmc.activity.CrashActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CrashActivity.enterActivity(this)
    }
}
