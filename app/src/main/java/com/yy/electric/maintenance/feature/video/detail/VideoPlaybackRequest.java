package com.yy.electric.maintenance.feature.video.detail;

import com.yy.electric.maintenance.base.BaseResult;

public class VideoPlaybackRequest extends BaseResult {

  public long startTime;

  public long endTime;

  public String videourl;

  @Override
  public String toString() {
    return "VideoPlaybackRequest{" +
            "startTime=" + startTime +
            ", endTime=" + endTime +
            ", videourl='" + videourl + '\'' +
            '}';
  }
}
