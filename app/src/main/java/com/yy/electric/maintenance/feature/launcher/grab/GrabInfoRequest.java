package com.yy.electric.maintenance.feature.launcher.grab;

import com.yy.electric.maintenance.base.BaseResult;

public class GrabInfoRequest extends BaseResult {

  public String repairStatus;

  @Override
  public String toString() {
    return "LoginRequest{" +
            "repairStatus='" + repairStatus + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
