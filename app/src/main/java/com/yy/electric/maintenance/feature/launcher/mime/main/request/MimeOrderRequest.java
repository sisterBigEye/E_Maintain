package com.yy.electric.maintenance.feature.launcher.mime.main.request;

import com.yy.electric.maintenance.base.BaseResult;

public class MimeOrderRequest extends BaseResult {

  public String user;

  public String type;

  @Override
  public String toString() {
    return "MimeOrderRequest{" +
            "user='" + user + '\'' +
            ", type='" + type + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
