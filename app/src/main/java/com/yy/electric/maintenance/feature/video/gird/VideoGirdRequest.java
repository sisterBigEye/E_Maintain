package com.yy.electric.maintenance.feature.video.gird;

import com.yy.electric.maintenance.base.BaseResult;

public class VideoGirdRequest extends BaseResult {

  public String username;

  @Override
  public String toString() {
    return "VideoGirdRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
