package com.file.matcher.calculationStrategy;

import com.file.matcher.model.DataType;

public class ScoreStrategyUtil {

  public static ScoreStrategy getInstance(DataType dataType) {
    if (dataType.equals(DataType.NUMERIC)) {
      return new NumericScoreStrategy();
    }
    if (dataType.equals(DataType.STRING)) {
      return new StringScoreStrategy();
    }
    if (dataType.equals(DataType.DATE)) {
      return new DateScoreStrategy();
    }
    return null;
  }
}
