package com.yy.electric.maintenance.feature.video.gird;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class VideoGirdPresenter implements VideoGirdContract.Presenter {
  private static final String TAG = "VideoGirdPresenter";
  private VideoGirdContract.View<VideoListInfo> view;
  private VideoGirdRequest mRequest;

  private ITask<VideoListInfo> task;

  VideoGirdPresenter(VideoGirdContract.View<VideoListInfo> view, VideoGirdRequest request) {
    this.view = view;
    this.mRequest = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    requestVideoList();
  }

  @Override
  public void requestVideoList() {
    if (task == null) {
      task = new RequestDataTask<>(VideoListInfo.class);
    }
    LogUtil.d(TAG, "requestVideoList() --- map = " + mRequest.toString());
    new RequestDataTask<>
            (VideoListInfo.class).startTask(mRequest, new Callback<VideoListInfo>() {
      @Override
      public void result(VideoListInfo videoListInfo) {
        view.videoListResult(videoListInfo);
      }
    });
  }
}
