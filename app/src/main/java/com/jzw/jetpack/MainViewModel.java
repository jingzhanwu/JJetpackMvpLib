package com.jzw.jetpack;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jingzhanwu
 * @date 2019/6/12 0012
 * @change
 * @describe viewmodel实现类
 **/
public class MainViewModel extends ViewModel {
    public MutableLiveData<String> liveData = new MutableLiveData();

    //修改数据值
    public void setText(String text) {
        liveData.setValue(text);
    }
    //map 变换,返回值是一个value
    public LiveData<String> name = Transformations.map(liveData, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input + "123";
        }
    });


    //switchMap 变换,返回值是LiveData
    public LiveData<String> address = Transformations.switchMap(liveData, new Function<String, LiveData<String>>() {
        @Override
        public LiveData<String> apply(String input) {
            MutableLiveData<String> data = new MutableLiveData<>();
            data.setValue(getNewData(input));
            return data;
        }
    });


    private String getNewData(String input) {
        return input + "新的数据";
    }

}
