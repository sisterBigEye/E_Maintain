package com.yy.electric.maintenance.base;

import com.google.gson.annotations.Expose;
import com.yy.electric.maintenance.api.HttpApi;

public class BaseResult implements IRequest {


  @Expose(serialize = false, deserialize = false)
  public transient String url;

  public transient String fistUrl;

  public transient String secondUrl;

  @Override
  public String getUrl() {
    if (fistUrl != null && secondUrl != null) {
      return fistUrl + HttpApi.getUserName() + secondUrl;
    }
    return url;
  }

  @Override
  public boolean isEmptyPostBody() {
    return false;
  }

  @Override
  public String toString() {
    return "BaseResult{" +
            "url='" + getUrl() + '\'' +
            '}';
  }
}
