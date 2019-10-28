package com.yy.electric.maintenance.util;

import java.text.DateFormat;
import java.util.Date;

public class TimeUtil {

  private static DateFormat sDateFormat = DateFormat.getDateTimeInstance();

  public static final long ONE_DAY_MILLISECOND = 1000 * 60 * 60 * 24;

  public static String getDateTime(long time) {
    if (time <= 0) {
      return "未知时间";
    }
    return sDateFormat.format(new Date(time));
  }
}
