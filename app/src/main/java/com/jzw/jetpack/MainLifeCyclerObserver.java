package com.jzw.jetpack;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jingzhanwu
 * @date 2019/6/12 0012
 * @change
 * @describe 主界面的lifeCycler
 **/
public class MainLifeCyclerObserver implements LifecycleObserver {

    private Lifecycle lifecycle;
    private MainCallbak callback;

    public MainLifeCyclerObserver(Lifecycle lifecycle, MainCallbak callback) {
        this.lifecycle = lifecycle;
        this.callback = callback;
    }

    /**
     * 注解方式，监听Activity或者fragment的onCreate方法
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        showToast("创建了界面");
        System.out.println("**********界面onCreate**********");
    }

    /**
     * 监听onResume生命周期函数
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        showToast("界面显示了");
        System.out.println("**********界面onResume**********");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

        System.out.println("**********界面onStart**********");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        System.out.println("**********界面onStop**********");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory() {
        System.out.println("**********界面onDestory**********");
    }


    /**
     * 业务处理函数
     *
     * @param text
     */
    private void showToast(String text) {
        callback.toast(text);
    }

    public interface MainCallbak {
        void toast(String text);
    }
}
