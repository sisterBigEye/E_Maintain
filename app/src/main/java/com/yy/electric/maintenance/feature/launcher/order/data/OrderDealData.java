package com.yy.electric.maintenance.feature.launcher.order.data;

import java.util.List;

public class OrderDealData {
  private static final String TAG = "OrderDealData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public String result;

    @Override
    public String toString() {
      return "Row{" +
              "result='" + result + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "OrderData{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
