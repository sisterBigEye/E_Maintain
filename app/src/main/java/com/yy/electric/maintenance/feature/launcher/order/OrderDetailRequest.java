package com.yy.electric.maintenance.feature.launcher.order;

import com.yy.electric.maintenance.base.BaseResult;

public class OrderDetailRequest extends BaseResult {

  public String repairSN;

  @Override
  public String toString() {
    return "OrderDetailRequest{" +
            "repairSN='" + repairSN + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
