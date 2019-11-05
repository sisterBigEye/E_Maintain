package com.yy.electric.maintenance.feature.video.detail;

import com.yy.electric.maintenance.base.BaseResult;

public class VideoControlRequest extends BaseResult {

  public int direction;

  public String channelNo;

  public String deviceSerial;

  public int speed;

  @Override
  public String toString() {
    return "VideoControlRequest{" +
            "direction='" + direction + '\'' +
            ", channelNo='" + channelNo + '\'' +
            ", deviceSerial='" + deviceSerial + '\'' +
            ", speed='" + speed + '\'' +
            '}';
  }
}
