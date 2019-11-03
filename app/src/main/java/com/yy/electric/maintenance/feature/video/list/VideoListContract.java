package com.yy.electric.maintenance.feature.video.list;

import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;

class VideoListContract {

  interface Presenter extends BasePresenter {

    void requestVideoList();

  }

  interface View<T> extends BaseView<Presenter> {

    void videoListResult(T t);

  }

}
