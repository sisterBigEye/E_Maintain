package com.yy.electric.maintenance.feature.login;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;

class LoginContract {

    interface Presenter extends BasePresenter {

        void login();

        void start();


    }

    interface View<T> extends BaseView<Presenter> {

        void showLoading();

        void dismissLoading();

        void loginSuccess(T t);

    }

}
