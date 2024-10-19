package com.file.matcher.validator;

import com.file.matcher.util.DataUtil;

public class NumberValidator extends DataValidator {

  public boolean validate(String bvalue, String svalue, int threshold) {
    if (!validateNull(bvalue, svalue))
      return false;

    Double score = Math.abs(DataUtil.getNumericValue(bvalue) - DataUtil.getNumericValue(svalue));
    if (score > threshold) {
      return false;
    }
    return true;
  }

}
