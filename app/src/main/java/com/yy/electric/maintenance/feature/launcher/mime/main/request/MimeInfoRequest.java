package com.yy.electric.maintenance.feature.launcher.mime.main.request;

import com.yy.electric.maintenance.base.BaseResult;

public class MimeInfoRequest extends BaseResult {

  public String username;

  @Override
  public String toString() {
    return "MimeInfoRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
