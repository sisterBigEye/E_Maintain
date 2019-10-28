package com.yy.electric.maintenance.feature.launcher.grab;

import com.google.gson.annotations.SerializedName;
import com.yy.electric.maintenance.base.BaseResult;

public class GrabRequest extends BaseResult {

  public String repairPersonInput;

  public String repairSN;

  public String exceptionDesc;

  @Override
  public String toString() {
    return "GrabRequest{" +
            "repairPersonInput='" + repairPersonInput + '\'' +
            ", repairSN='" + repairSN + '\'' +
            ", exceptionDesc='" + exceptionDesc + '\'' +
            '}';
  }
}
