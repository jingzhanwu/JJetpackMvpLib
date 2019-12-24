package com.jetpack.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe 默认的view代理实现，一般上层只需要实现此类即可，
 * 这里可定义一些通用的方法供子类直接使用
 * 如果需要高度自定义则可自行实现@{@link IViewDelegate}
 **/
public abstract class AppViewDelegate implements IViewDelegate{
    //缓存所有view
    protected final SparseArray<View> mViews=new SparseArray<View>();
    //根view
    protected View rootView;

    /**
     * 实现类需要实现，返回真正的布局id
     * @return
     */
    public abstract int getRootLayoutId();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId=getRootLayoutId();
        rootView=inflater.inflate(rootLayoutId,container,false);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView){
        this.rootView=rootView;
    }

    @Override
    public void initViews() {
        //TODO 初始化控件
    }

    public <T extends View> T bindView(int id){
        T view =(T)mViews.get(id);
        if(view==null){
            view=rootView.findViewById(id);
            mViews.put(id,view);
        }
        return view;
    }

    /**
     * 返回对应view控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T get(int id){
        return (T)bindView(id);
    }

    /**
     * 绑定点击监听器，可同时绑定多个
     * @param listener
     * @param ids
     */
    public void setOnClickListener(View.OnClickListener listener,int... ids){
        if(ids==null){
            return;
        }
        for(int id:ids){
            get(id).setOnClickListener(listener);
        }
    }

    /**
     * 返回当前Activity
     * @param <T>
     * @return
     */
    public <T extends Activity> T getActivity(){
        return (T)rootView.getContext();
    }

    /**
     * 弹出toast提示
     * @param msg
     */
    public void toast(String msg){
        Toast.makeText(rootView.getContext().getApplicationContext(),msg,Toast.LENGTH_SHORT)
                .show();
    }
}
