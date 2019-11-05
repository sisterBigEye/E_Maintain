package com.yy.electric.maintenance.feature.video.gird;

import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;

class VideoGirdContract {

  interface Presenter extends BasePresenter {

    void requestVideoList();

  }

  interface View<T> extends BaseView<Presenter> {

    void videoListResult(T t);

  }

}
