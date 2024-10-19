package com.file.matcher.calculationStrategy;

import com.file.matcher.util.DataUtil;

import java.util.Date;

public class DateScoreStrategy implements ScoreStrategy {

  public Double calculateScore(String bvalue,String svalue) {
    Date date1 = DataUtil.getDateValue(bvalue);
    Date date2 = DataUtil.getDateValue(svalue);

    // Calculate the difference in milliseconds
    long differenceInMilliseconds = Math.abs(date1.getTime() - date2.getTime());

    // Convert milliseconds to days
    long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);

    return Double.valueOf(differenceInDays);
  }
}
