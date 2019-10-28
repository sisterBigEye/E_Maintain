package com.yy.electric.maintenance.feature.launcher.mime.modify.info;


import com.yy.electric.maintenance.base.BaseResult;

public class ModifyAvatarRequest extends BaseResult {

  public String username;

  public String userAvatar;

  @Override
  public String toString() {
    return "ModifyAvatarRequest{" +
            "username='" + username + '\'' +
            ", userAvatar='" + userAvatar + '\'' +
            '}';
  }
}
