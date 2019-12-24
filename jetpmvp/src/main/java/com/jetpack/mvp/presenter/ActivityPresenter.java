package com.jetpack.mvp.presenter;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jetpack.mvp.InputKeyboardUtil;
import com.jetpack.mvp.annotation.BindDataBinder;
import com.jetpack.mvp.databinder.IDataBinder;
import com.jetpack.mvp.model.IModel;
import com.jetpack.mvp.view.IViewDelegate;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe P 层抽象，也是上层Activity必须实现的，在这里实现业务逻辑处理
 **/
public abstract class ActivityPresenter<V extends IViewDelegate> extends AppCompatActivity {

    //view代理对象
    protected V viewDelegate;
    //缓存所有的dataBinder
    protected ArrayMap<String, IDataBinder> dataBinderMap;
    /**
     * 是否可以隐藏软键盘
     */
    private boolean isShouldHideInput = true;

    /**
     * 返回具体的view代理实现类class
     *
     * @return
     */
    public abstract Class<V> getViewDelegateClass();

    /**
     * presenter初始化准备完成回调
     *
     * @param savedInstanceState
     */
    public abstract void onPresenterCreated(Bundle savedInstanceState);


    public ActivityPresenter() {
        initDataBinderAndViewDelegate();
    }

    /**
     * 初始化dataBinder和viewDelegate
     */
    private void initDataBinderAndViewDelegate() {
        try {
            //初始化view代理对象
            Class<V> delegate = getViewDelegateClass();
            if (delegate != null) {
                viewDelegate = delegate.newInstance();
            }
            //找到当前类上的注解
            BindDataBinder bindDataBinder = getClass().getAnnotation(BindDataBinder.class);
            if (null == bindDataBinder) {
                throw new RuntimeException("BindDataBinder is invalid");
            }
            if (null == dataBinderMap) {
                dataBinderMap = new ArrayMap<>();
                //初始化并缓存所有指定的dataBinder
                for (Class<? extends IDataBinder> clazz : bindDataBinder.dataBinder()) {
                    IDataBinder dataBinder = clazz.newInstance();
                    dataBinderMap.put(clazz.getSimpleName(), dataBinder);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找model绑定的dataBinder对象
     * 根据model的注解指定的查找
     *
     * @param model
     * @return
     */
    protected IDataBinder getDataBinder(IModel model) {
        BindDataBinder bindDataBinder = model.getClass().getAnnotation(BindDataBinder.class);
        if (null == bindDataBinder) {
            throw new RuntimeException("Not find BindDataBinder");
        }
        return dataBinderMap.get(bindDataBinder.dataBinder()[0].getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        initToolbar();
        viewDelegate.initViews();
        bindEventListener();
        onPresenterCreated(savedInstanceState);
    }


    public void bindEventListener() {
    }

    protected void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolbar();
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null == viewDelegate || null == dataBinderMap) {
            initDataBinderAndViewDelegate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        viewDelegate = null;
        if (dataBinderMap != null) {
            dataBinderMap.clear();
        }
        super.onDestroy();
    }

    /**
     * 通知数据改变
     *
     * @param model
     */
    public void notifyModelChange(IModel model) {
        IDataBinder dataBinder = getDataBinder(model);
        if (null == dataBinder) {
            throw new RuntimeException("Can not find DataBinder,just check your Presenter`s annotation");
        }
        dataBinder.notifyModelChanged(viewDelegate, model);
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
