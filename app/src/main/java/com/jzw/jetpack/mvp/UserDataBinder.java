package com.jzw.jetpack.mvp;

import com.jetpack.mvp.databinder.IDataBinder;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe 绑定数据
 **/
public class UserDataBinder implements IDataBinder<UserView, UserModel> {
    @Override
    public void notifyModelChanged(UserView viewDelegate, UserModel data) {
        //真正通知View 更新UI，根据view层提供的方法设置数据
        viewDelegate.setUser(data.getUsers());
    }
}
