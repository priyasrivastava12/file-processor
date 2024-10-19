package com.file.matcher.calculationStrategy;

import com.file.matcher.util.DataUtil;

public class DefaultScoreStrategy implements ScoreStrategy {

  public Double calculateScore(String bvalue, String svalue) {
    return Double.MAX_VALUE;
  }
}
