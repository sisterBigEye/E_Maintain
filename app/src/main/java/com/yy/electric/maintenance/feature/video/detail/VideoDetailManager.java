package com.yy.electric.maintenance.feature.video.detail;

import com.yy.electric.maintenance.feature.video.list.VideoListInfo;

public class VideoDetailManager {

  private static volatile VideoDetailManager instance;

  private VideoListInfo.Row mVideoDetailInfo;

  public static VideoDetailManager getInstance() {
    if (instance == null) {
      synchronized (VideoDetailManager.class) {
        if (instance == null) {
          instance = new VideoDetailManager();
        }
      }
    }
    return instance;
  }

  private VideoDetailManager() {

  }

  public VideoListInfo.Row getVideoDetailInfo() {
    return mVideoDetailInfo;
  }

  public void setVideoDetailInfo(VideoListInfo.Row videoDetailInfo) {
    this.mVideoDetailInfo = videoDetailInfo;
  }
}
