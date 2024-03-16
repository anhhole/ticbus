package com.ticbus.backend.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author AnhLH
 */
public class DateUtils {
  private static DateUtils instance;

  static {
    try {
      instance = new DateUtils();
    } catch (Exception e) {
      throw new RuntimeException("Exception occurred in creating singleton instance");
    }
  }

  private DateUtils() {
  }

  public static DateUtils getInstance() {
    return instance;
  }

  public boolean isSameMonth(Date date1, Date date2){
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date1);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(date2);
    return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
  }
  public boolean isSameYear(Date date1, Date date2){
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date1);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(date2);
    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
  }
}
