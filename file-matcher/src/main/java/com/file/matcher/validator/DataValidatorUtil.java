package com.file.matcher.validator;

import com.file.matcher.calculationStrategy.DateScoreStrategy;
import com.file.matcher.calculationStrategy.NumericScoreStrategy;
import com.file.matcher.calculationStrategy.ScoreStrategy;
import com.file.matcher.calculationStrategy.StringScoreStrategy;
import com.file.matcher.model.DataType;

public class DataValidatorUtil {

  public static DataValidator getInstance(DataType dataType) {
    if (dataType.equals(DataType.NUMERIC)) {
      return new NumberValidator();
    }
    if (dataType.equals(DataType.STRING)) {
      return new StringValidator();
    }
    if (dataType.equals(DataType.DATE)) {
      return new DateValidator();
    }
    return null;
  }
}
