package com.android.lmc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.lmc.JetpackViewModel;
import com.android.lmc.R;
import com.android.lmc.tool.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JetpackActivity extends AppCompatActivity {
    private JetpackViewModel mViewModel;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    public static void enterActivity(Context context){
        Intent intent = new Intent(context, JetpackActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_jetpack);
        ButterKnife.bind(JetpackActivity.this);
        addLifeCycle();
        addViewModel();
        addLiveData();
    }

    private void addLifeCycle(){
        getLifecycle().addObserver(new JetpackActivityLifeObserver(new JetpackActivityLifeObserver.Callback() {
            @Override
            public void updateUi() {
                //todo
            }
        }));
    }

    private void addViewModel(){
        ViewModelProvider vmProvider = new ViewModelProvider(JetpackActivity.this, new ViewModelProvider.NewInstanceFactory());
        mViewModel = vmProvider.get(JetpackViewModel.class);
    }

    private void addLiveData(){
        mViewModel.getTime().observe(JetpackActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mTvTime.setText(mViewModel.getTime().getValue());
            }
        });
    }

    @OnClick(R.id.tv_time)
    void onTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        mViewModel.setDate(sdf.format(new Date()));
//        mTvTime.setText(mViewModel.getDate());
        mViewModel.getTime().setValue(sdf.format(new Date()));
    }



    @Override
    public void onResume(){
        super.onResume();
//        if(!StringUtil.isEmpty(mViewModel.getDate())){
//            mTvTime.setText(mViewModel.getDate());
//        }
    }
}
