package com.file.matcher.calculationStrategy;

import com.file.matcher.util.DataUtil;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

public class StringScoreStrategy implements ScoreStrategy {

  public Double calculateScore(String bvalue, String svalue) {
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    int score = levenshteinDistance.apply(bvalue, svalue);
    return Double.parseDouble(String.valueOf(score));
  }
}

