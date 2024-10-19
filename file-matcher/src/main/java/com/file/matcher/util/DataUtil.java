package com.file.matcher.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {


  public static boolean isNumeric(String value) {
    String copy = new String(value);

    try {
      Double.parseDouble(value);
      return true;
    } catch (NumberFormatException e) {
      try {
        copy = copy.replace(",", "");
        Double.parseDouble(copy);
        return true;
      } catch (NumberFormatException ex) {
        return false;
      }
    }
  }

  public static boolean isDate(String value) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    dateFormat.setLenient(false);

    try {
      dateFormat.parse(value);
      return true;
    } catch (ParseException e) {
      dateFormat = new SimpleDateFormat("dd-MM-yyyy");
      try {
        dateFormat.parse(value);
        return true;
      } catch (ParseException ex) {
        return false;
      }
    }
  }


  public static Date getDateValue(String value) {
    Date ans = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    try {
      ans = dateFormat.parse(value);
    } catch (ParseException e) {
      dateFormat = new SimpleDateFormat("dd-MM-yyyy");
      try {
        ans = dateFormat.parse(value);
      } catch (ParseException ex) {
        throw new RuntimeException(ex);
      }
    }
    return ans;
  }

  public static Double getNumericValue(String num) {
    Double ans = 0.0;
    try {
      ans = Double.parseDouble(num);
    } catch (NumberFormatException e) {
      try {
        num = num.replace(",", "");
        ans = Double.parseDouble(num);
      } catch (NumberFormatException ex) {
        return ans;
      }
    }
    return ans;
  }
}
