package com.jzw.jetpack.mvvm;

import com.jetpack.mvp.model.IModel;

import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe 页面数据模型
 **/
public class StudentModel implements IModel {
    private List<Student> users;
    private String city;

    public List<Student> getUsers() {
        return users;
    }

    public void setUsers(List<Student> users) {
        this.users = users;
        city = users.get(0).getName();
    }

    @Override
    public void clearData() {
        //TODO 清理数据

    }
}
