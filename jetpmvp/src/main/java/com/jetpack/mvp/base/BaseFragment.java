package com.jetpack.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jetpack.mvp.annotation.BindViewModel;
import com.jetpack.mvp.model.IModel;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe Fragment基类，基本接口封装
 * 作为MVP 的V层使用
 **/
public abstract class BaseFragment<M extends IModel, VM extends BaseViewModel<M>> extends Fragment {

    //处理数据的viewModel对象
    protected VM viewModel;

    protected View rootView;

    /**
     * 返回根布局id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化准备完成回调
     *
     * @param savedInstanceState
     */
    public abstract void onInitViews(Bundle savedInstanceState);

    /**
     * model数据变化的时候回调
     *
     * @param model
     */
    public abstract void onModelChanged(M model);

    private void init(Bundle savedInstanceState) {
        initViewModel();
        onInitViews(savedInstanceState);
        bindEventListener();
    }

    private void initViewModel() {
        BindViewModel bindViewModel = getClass().getAnnotation(BindViewModel.class);
        if (bindViewModel == null) {
            throw new RuntimeException("Not bind viewModel");
        }
        viewModel = (VM) ViewModelProviders.of(this).get(bindViewModel.value());
        viewModel.getModelLiveData().observe(this, new Observer<M>() {
            @Override
            public void onChanged(M m) {
                onModelChanged(m);
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            throw new RuntimeException("RootView layout is null");
        }
        rootView = inflater.inflate(layoutId, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null == viewModel) {
            init(savedInstanceState);
        }
    }

    public void bindEventListener() {

    }

    /**
     * 绑定点击监听器，可同时绑定多个
     *
     * @param listener
     * @param ids
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            rootView.findViewById(id).setOnClickListener(listener);
        }
    }
}
