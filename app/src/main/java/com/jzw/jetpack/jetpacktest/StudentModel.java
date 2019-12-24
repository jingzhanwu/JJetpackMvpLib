package com.jzw.jetpack.jetpacktest;

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

    public List<Student> getUsers() {
        return users;
    }

    public void setUsers(List<Student> users) {
        this.users = users;
    }

    @Override
    public void clearData() {

    }
}
