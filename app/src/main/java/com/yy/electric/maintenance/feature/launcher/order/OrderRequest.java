package com.yy.electric.maintenance.feature.launcher.order;

import com.yy.electric.maintenance.base.BaseResult;

public class OrderRequest extends BaseResult {

  public String user;

  @Override
  public String toString() {
    return "LoginRequest{" +
            "user='" + user + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
