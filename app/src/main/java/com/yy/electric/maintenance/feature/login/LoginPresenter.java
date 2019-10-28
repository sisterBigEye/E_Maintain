package com.yy.electric.maintenance.feature.login;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";
    private LoginContract.View<UserInfo> view;
    private LoginRequest request;
    private ITask<UserInfo> loginTask;

    LoginPresenter(LoginContract.View<UserInfo> view, LoginRequest request) {
        this.view = view;
        this.request = request;
        this.view.setPersonal(this);
    }

    @Override
    public void login() {

        if (view == null || request == null) {
            LogUtil.e(TAG, "login() --- view is null");
            return;
        }
        view.showLoading();

        if (loginTask == null) {
            loginTask = new RequestDataTask<>(UserInfo.class);
        }
        LogUtil.d(TAG, "login() --- map = " + request.toString());
        loginTask.startTask(request, new Callback<UserInfo>() {

            @Override
            public void result(UserInfo userInfo) {
                view.dismissLoading();
                view.loginSuccess(userInfo);
            }
        });
    }

    @Override
    public void start() {
        login();
    }
}
