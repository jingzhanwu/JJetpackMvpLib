package com.jetpack.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe view 层代理接口，定义构造view视图基础
 * 接口
 **/
public interface IViewDelegate {
    /**
     * 加載xml，創建rootView
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 返回当前根视图view
     * @return
     */
    View getRootView();
    /**
     * 返回选项菜单布局文件
     * @return
     */
    int getOptionsMenuId();
    /**
     * 如果有toobar，返回toolBar
     * @return
     */
    Toolbar getToolbar();

    /**
     * 初始化组件,子类实现
     */
    void initViews();

}
