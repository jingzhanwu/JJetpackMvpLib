package com.jetpack.mvp.databinder;

import com.jetpack.mvp.model.IModel;
import com.jetpack.mvp.view.IViewDelegate;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe 数据绑定接口
 **/
public interface IDataBinder<V extends IViewDelegate,M extends IModel> {
    /**
     * 將数据与view绑定，数据改变的时候，框架根据数据与view的绑定关系自动改变view
     * 数据从而改变UI显示
     * model数据改变的时候回调此方法
     *
     * @param viewDelegate 视图层代理
     * @param data 数据模型对象
     */
    void notifyModelChanged(V viewDelegate,M data);
}
