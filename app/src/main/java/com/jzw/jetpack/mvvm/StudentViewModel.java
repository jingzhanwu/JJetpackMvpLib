package com.jzw.jetpack.mvvm;

import android.app.Application;

import com.jetpack.mvp.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe describe
 **/
public class StudentViewModel extends BaseViewModel<StudentModel> {
    private StudentModel model = new StudentModel();

    public StudentViewModel(Application application) {
        super(application);
    }

    public void initUser() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student user = new Student("Name" + i, "18", "西安");
            list.add(user);
        }
        model.setUsers(list);
        notifyDataChanged(model);
    }
}
