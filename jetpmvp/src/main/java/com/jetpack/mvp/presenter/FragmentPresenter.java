package com.jetpack.mvp.presenter;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jetpack.mvp.annotation.BindDataBinder;
import com.jetpack.mvp.databinder.IDataBinder;
import com.jetpack.mvp.model.IModel;
import com.jetpack.mvp.view.IViewDelegate;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe P 层抽象，也是上层Fragment必须实现的，在这里实现业务逻辑处理
 **/
public abstract class FragmentPresenter<V extends IViewDelegate> extends Fragment {

    //view代理对象
    protected V viewDelegate;
    //缓存所有的dataBinder
    protected ArrayMap<String, IDataBinder> dataBinderMap;

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
        } catch (java.lang.InstantiationException e) {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinderAndViewDelegate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDelegate.create(getLayoutInflater(), container, savedInstanceState);
        return viewDelegate.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDelegate.initViews();
        bindEventListener();
        onPresenterCreated(savedInstanceState);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null == viewDelegate || null == dataBinderMap) {
            initDataBinderAndViewDelegate();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewDelegate.getOptionsMenuId() != 0) {
            inflater.inflate(viewDelegate.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
        if (dataBinderMap != null) {
            dataBinderMap.clear();
        }
    }

    /**
     * 子类实现，绑定监听器
     */
    public void bindEventListener() {
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
}
