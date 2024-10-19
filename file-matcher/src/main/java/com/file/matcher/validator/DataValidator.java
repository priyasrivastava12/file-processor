package com.file.matcher.validator;

public abstract class DataValidator {

  public abstract boolean validate(String bvalue, String svalue, int threshold);

  public boolean validateNull(String bvalue, String svalue) {
    if ((bvalue == null && svalue != null) || (svalue == null && bvalue != null))
      return false;
    return true;
  }

}
