package com.yy.electric.maintenance.feature.video.gird;

import java.util.List;

public class VideoListInfo {
  private static final String TAG = "VideoListInfo";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public transient boolean isSelect;

    public String cameratype;

    public String specificname;

    public String username;

    public String videourl;

    @Override
    public String toString() {
      return "Row{" +
              "isSelect=" + isSelect +
              ", cameratype='" + cameratype + '\'' +
              ", specificname='" + specificname + '\'' +
              ", username='" + username + '\'' +
              ", videourl='" + videourl + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "VideoListInfo{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
