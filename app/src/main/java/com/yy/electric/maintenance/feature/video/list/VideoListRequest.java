package com.yy.electric.maintenance.feature.video.list;

import com.yy.electric.maintenance.base.BaseResult;

public class VideoListRequest extends BaseResult {

  public String username;

  @Override
  public String toString() {
    return "VideoListRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
