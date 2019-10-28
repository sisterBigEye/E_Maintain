package com.yy.electric.maintenance.feature.launcher.grab;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabCheckResultData;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabData;

class GrabContract {

  interface Presenter extends BasePresenter {

    void loadGrabInfoData();

    void checkGrab();

    void grabOrder();

  }

  interface View<T> extends BaseView<Presenter> {

    void updateGrabInfoData(T t);

    void checkGrabResult(GrabCheckResultData data);

    void grabResult(GrabData data);

  }

}
