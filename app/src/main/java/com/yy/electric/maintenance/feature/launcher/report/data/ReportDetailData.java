package com.yy.electric.maintenance.feature.launcher.report.data;

import java.util.List;

public class ReportDetailData {

  private static final String TAG = "OrderDetailData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public int id;

    public String repairstatus;

    public String usersource;

    public String devicesource;

    public String repairsn;

    public long createtime;

    public long receivcetime;

    public long handletime;

    public long repairfinishtime;

    public long managefinishtime;

    public String exceptiondesc;

    public String createperson;

    public String receiveperson;

    public String handleresult;

    public String remarks;

    @Override
    public String toString() {
      return "Row{" +
              "id=" + id +
              ", repairstatus='" + repairstatus + '\'' +
              ", usersource='" + usersource + '\'' +
              ", devicesource='" + devicesource + '\'' +
              ", repairsn='" + repairsn + '\'' +
              ", createtime=" + createtime +
              ", receivcetime=" + receivcetime +
              ", handletime=" + handletime +
              ", repairfinishtime=" + repairfinishtime +
              ", managefinishtime=" + managefinishtime +
              ", exceptiondesc='" + exceptiondesc + '\'' +
              ", createperson='" + createperson + '\'' +
              ", createperson='" + createperson + '\'' +
              ", handleresult='" + handleresult + '\'' +
              ", remarks='" + remarks + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "OrderDetailData{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
