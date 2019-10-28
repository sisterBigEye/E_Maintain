package com.yy.electric.maintenance.feature.launcher.grab.data;

import java.util.List;

public class GrabCheckResultData {
  private static final String TAG = "GrabCheckResultData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public transient boolean isSelect;

    public int id;

    public String repairstatus;

    public String usersource;

    public String devicesource;

    public String repairsn;

    public String createtime;

    public String exceptiondesc;

    public String createperson;


    @Override
    public String toString() {
      return "Row{" +
              "isSelect=" + isSelect +
              ", id=" + id +
              ", repairstatus='" + repairstatus + '\'' +
              ", usersource='" + usersource + '\'' +
              ", devicesource='" + devicesource + '\'' +
              ", repairsn='" + repairsn + '\'' +
              ", createtime='" + createtime + '\'' +
              ", exceptiondesc='" + exceptiondesc + '\'' +
              ", createperson='" + createperson + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "GrabCheckResultData{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}