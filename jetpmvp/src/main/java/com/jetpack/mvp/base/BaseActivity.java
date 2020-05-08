package com.jetpack.mvp.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jetpack.mvp.InputKeyboardUtil;
import com.jetpack.mvp.annotation.BindViewModel;
import com.jetpack.mvp.model.IModel;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe Activity基类，基本接口封装
 * 作为MVP 的V层使用
 **/
public abstract class BaseActivity<M extends IModel, VM extends BaseViewModel<M>> extends AppCompatActivity {

    //处理数据的viewModel对象
    protected VM viewModel;

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


    /**
     * 是否可以隐藏软键盘
     */
    private boolean isShouldHideInput = true;


    private void init(Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            throw new RuntimeException("RootView layout is null");
        }
        setContentView(layoutId);
        initToolbar();
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null == viewModel) {
            init(savedInstanceState);
        }
    }

    public void bindEventListener() {

    }

    protected void initToolbar() {

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
            findViewById(id).setOnClickListener(listener);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShouldHideInput) {
                // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                View v = getCurrentFocus();
                if (InputKeyboardUtil.isShouldHideInput(v, ev)) {
                    InputKeyboardUtil.hideSoftInput(this, v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否开启 点击输入法之外的地方 自动隐藏软键盘功能，默认是开启的
     *
     * @param hide
     */
    public void setShouldHideInput(boolean hide) {
        this.isShouldHideInput = hide;
    }
}
