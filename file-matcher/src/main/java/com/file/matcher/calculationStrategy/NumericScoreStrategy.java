package com.file.matcher.calculationStrategy;

import com.file.matcher.util.DataUtil;

public class NumericScoreStrategy implements ScoreStrategy {

  public Double calculateScore(String bvalue,String svalue) {
    Double score = Math.abs(DataUtil.getNumericValue(bvalue) - DataUtil.getNumericValue(svalue));
    return score;
  }
}
