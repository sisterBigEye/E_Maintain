package com.yy.electric.maintenance.feature.launcher.grab;

import com.yy.electric.maintenance.base.BaseResult;

public class GrabCheckRequest extends BaseResult {

  public String repairSN;

  @Override
  public String toString() {
    return "GrabCheckRequest{" +
            "repairSN='" + repairSN + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
