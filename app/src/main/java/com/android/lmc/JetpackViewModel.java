package com.android.lmc;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class JetpackViewModel extends ViewModel {
    private String date;
    private MutableLiveData<String> mTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MutableLiveData<String> getTime(){
        if(mTime == null){
            mTime = new MutableLiveData<>();
        }
        return mTime;
    }

    private void hehe(){
        LiveData<Integer> result = Transformations.map(mTime, new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input == null? 0: input.length();
            }
        });
        LiveData<TestModel> result2 = Transformations.switchMap(mTime, new Function<String, LiveData<TestModel>>() {
            @Override
            public LiveData<TestModel> apply(String input) {
                //todo 比如根据id查询数据库model
                return null;
            }
        });
    }

    class TestModel{
        public String time;
        public int year;
    }
}
