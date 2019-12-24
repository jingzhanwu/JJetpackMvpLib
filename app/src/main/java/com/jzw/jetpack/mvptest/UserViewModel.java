package com.jzw.jetpack.mvptest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe describe
 **/
public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        initUser();
    }

    public void initUser() {
        if (users == null) {
            users = new MutableLiveData<>();
        }
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User("Name" + i, "18", "西安");
            list.add(user);
        }
        users.setValue(list);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
