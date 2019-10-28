package com.yy.electric.maintenance.feature.launcher.report;

import com.yy.electric.maintenance.base.BaseResult;

public class ReportDetailRequest extends BaseResult {

  @Override
  public boolean isEmptyPostBody() {
    return true;
  }

  @Override
  public String toString() {
    return "LoginRequest{" +
            '}';
  }

}
