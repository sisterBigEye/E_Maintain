package com.yy.electric.maintenance.feature.launcher.order;

import com.yy.electric.maintenance.base.BaseResult;

public class OverOrderRequest extends BaseResult {

  public String repairSN;

  public String handleResult;

  public String remarks;

  @Override
  public String toString() {
    return "OverOrderRequest{" +
            "repairSN='" + repairSN + '\'' +
            ", handleResult='" + handleResult + '\'' +
            ", remarks='" + remarks + '\'' +
            '}';
  }
}
