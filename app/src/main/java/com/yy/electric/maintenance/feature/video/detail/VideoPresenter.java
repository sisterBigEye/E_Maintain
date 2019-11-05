package com.yy.electric.maintenance.feature.video.detail;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.task.RequestDataTask;

public class VideoPresenter implements VideoContract.Presenter {
  private static final String TAG = "VideoPresenter";
  private VideoContract.View view;
  private VideoControlRequest mControlRequest;
  private VideoChannelRequest mChannelRequest;
  private VideoPlaybackRequest mVideoPlaybackRequest;

  private ITask<VideoControlResult> videoControlTask;
  private ITask<VideoChannel> videoChannelTask;
  private ITask<VideoPlaybackResult> videoPlaybackTask;

  VideoPresenter(VideoContract.View view,
                 VideoControlRequest controlRequest,
                 VideoChannelRequest channelRequest,
                 VideoPlaybackRequest videoPlaybackRequest) {
    this.view = view;
    this.mControlRequest = controlRequest;
    this.mChannelRequest = channelRequest;
    this.mVideoPlaybackRequest = videoPlaybackRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    requestChannel();
  }

  @Override
  public void requestChannel() {
    if (videoChannelTask == null) {
      videoChannelTask = new RequestDataTask<>(VideoChannel.class);
    }
    videoChannelTask.startTask(mChannelRequest, new Callback<VideoChannel>() {
      @Override
      public void result(VideoChannel channel) {
        view.channelResult(channel);
      }

    });
  }

  @Override
  public void controlVideo() {
    if (videoControlTask == null) {
      videoControlTask = new RequestDataTask<>(VideoControlResult.class);
    }
    videoControlTask.startTask(mControlRequest, new Callback<VideoControlResult>() {
      @Override
      public void result(VideoControlResult result) {

      }

    });
  }

  @Override
  public void playbackRequest() {
    if (videoPlaybackTask == null) {
      videoPlaybackTask = new RequestDataTask<>(VideoPlaybackResult.class);
    }
    videoPlaybackTask.startTask(mVideoPlaybackRequest, new Callback<VideoPlaybackResult>() {
      @Override
      public void result(VideoPlaybackResult result) {
        view.playbackResult(result);
      }
    });
  }
}
