package com.yy.electric.maintenance.feature.video.detail;

import com.yy.electric.maintenance.feature.video.gird.VideoListInfo;

import java.util.List;

public class VideoDetailManager {

  private static volatile VideoDetailManager instance;

  private VideoListInfo.Row mVideoDetailInfo;

  private List<VideoListInfo.Row> mVideoInfoList;

  private String mVideoUserName;

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

  public String getVideoUserName() {
    return mVideoUserName;
  }

  public void setVideoUserName(String videoUserName) {
    this.mVideoUserName = videoUserName;
  }

  public List<VideoListInfo.Row> getVideoInfoList() {
    return mVideoInfoList;
  }

  public void setVideoInfoList(List<VideoListInfo.Row> videoInfoList) {
    this.mVideoInfoList = videoInfoList;
  }
}
