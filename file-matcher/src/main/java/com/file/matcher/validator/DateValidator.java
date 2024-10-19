package com.file.matcher.validator;

import com.file.matcher.util.DataUtil;

import java.util.Date;

public class DateValidator extends DataValidator {

  public boolean validate(String bvalue, String svalue, int threshold) {
    if (!validateNull(bvalue, svalue))
      return false;
    Date date1 = DataUtil.getDateValue(bvalue);
    Date date2 = DataUtil.getDateValue(svalue);

    // Calculate the difference in milliseconds
    long differenceInMilliseconds = Math.abs(date1.getTime() - date2.getTime());

    // Convert milliseconds to days
    long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);

    if (differenceInDays > threshold) {
      return false;
    }
    return true;
  }

}
