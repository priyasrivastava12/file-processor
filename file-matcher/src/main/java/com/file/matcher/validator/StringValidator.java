package com.file.matcher.validator;

import com.file.matcher.util.DataUtil;

public class StringValidator extends DataValidator {

  public boolean validate(String bvalue, String svalue, int threshold) {
    if (!validateNull(bvalue, svalue))
      return false;

    if ((bvalue.length() == 0 && svalue.length() != 0) || (svalue.length() == 0 && bvalue.length() != 0)) {
      return false;
    }
    return true;
  }

}
