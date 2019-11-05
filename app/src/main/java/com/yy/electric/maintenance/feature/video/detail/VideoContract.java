package com.yy.electric.maintenance.feature.video.detail;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;

class VideoContract {

  interface Presenter extends BasePresenter {

    void requestChannel();

    void controlVideo();

    void playbackRequest();

  }

  interface View extends BaseView<Presenter> {

    void channelResult(VideoChannel channel);

    void controlVideoResult(VideoControlResult result);

    void playbackResult(VideoPlaybackResult result);


  }

}
