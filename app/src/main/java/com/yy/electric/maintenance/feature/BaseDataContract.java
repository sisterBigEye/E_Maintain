package com.yy.electric.maintenance.feature;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;

public class BaseDataContract {

    public interface Presenter<T> extends BasePresenter<T> {

        void request();

    }

    public interface View<T> extends BaseView<Presenter> {

        void requestStart();

        void result(T result);

        void requestEnd();

    }

}
