package com.jzw.jetpack.mvptest;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jetpack.mvp.annotation.BindDataBinder;
import com.jetpack.mvp.presenter.ActivityPresenter;

import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe describe
 **/
@BindDataBinder(dataBinder = UserDataBinder.class)
public class UserListActivity extends ActivityPresenter<UserView> {

    private UserModel model;

    @Override
    public Class<UserView> getViewDelegateClass() {
        return UserView.class;
    }

    @Override
    public void onPresenterCreated(Bundle savedInstanceState) {
        model = new UserModel();
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //设置数据
                model.setUsers(users);
                //通知更新UI
                notifyModelChange(model);
            }
        });
    }
}
