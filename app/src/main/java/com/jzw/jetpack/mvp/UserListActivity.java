package com.jzw.jetpack.mvp;

import android.os.Bundle;
import android.view.View;

import com.jetpack.mvp.annotation.BindDataBinder;
import com.jetpack.mvp.presenter.ActivityPresenter;
import com.jzw.jetpack.R;

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

    /**
     * 所有的初始化工作处理完毕回调
     *
     * @param savedInstanceState
     */
    @Override
    public void onPresenterCreated(Bundle savedInstanceState) {
        model = new UserModel();
        User user = new User("刘三刀", "18", "上海");
        model.addUser(user);
        model.setUserName(user.getName());
        //通知更新UI
        notifyModelChange(model);
    }

    @Override
    public void bindEventListener() {
        super.bindEventListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = viewDelegate.getUserName();
                User u = new User(userName, "23", "西安");
                model.addUser(u);
                model.setUserName(userName);
                notifyModelChange(model);
            }
        }, R.id.btnInsert);
    }
}
