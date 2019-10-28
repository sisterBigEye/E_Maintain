package com.yy.electric.maintenance.feature.launcher.order;

import com.yy.electric.maintenance.base.BaseResult;

public class OrderDealRequest extends BaseResult {

  public String repairSN;

  @Override
  public String toString() {
    return "OrderDealRequest{" +
            "repairSN='" + repairSN + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
