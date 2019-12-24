package com.jetpack.mvp.base;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jetpack.mvp.model.IModel;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jzw
 * @date 2019/12/10
 * @change
 * @describe viewModel 接口，负责model与view直接的通信，
 * repository数据处理，更好的将逻辑与UI分离
 **/
public class BaseViewModel<M extends IModel> extends AndroidViewModel {
    //可以被观察的数据，就是model
    private MutableLiveData<M> modelLiveData;

    public BaseViewModel(Application application) {
        super(application);
        modelLiveData = new MutableLiveData<>();
    }

    public void notifyDataChanged(M data) {
        if (modelLiveData == null) {
            modelLiveData = new MutableLiveData<>();
        }
        modelLiveData.postValue(data);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    protected LiveData<M> getModelLiveData() {
        return modelLiveData;
    }
}
