package com.jzw.jetpack.mvptest;

import com.jetpack.mvp.annotation.BindDataBinder;
import com.jetpack.mvp.model.IModel;

import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe 页面数据模型
 **/
@BindDataBinder(dataBinder = UserDataBinder.class)
public class UserModel implements IModel {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void clearData() {

    }
}
