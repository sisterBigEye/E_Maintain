package com.yy.electric.maintenance.feature.launcher.mime.modify.password;

import com.yy.electric.maintenance.base.BaseResult;

public class ModifyPasswordRequest extends BaseResult {

  public String username;

  public String password;

  @Override
  public String toString() {
    return "ModifyPasswordRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
