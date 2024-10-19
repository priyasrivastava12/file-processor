package com.file.matcher.util;

import com.file.matcher.model.Result;

public class ScoreUtils {

  public static void enrichCategoryFromScore(Result result) {
    Double score = result.getScore();

    if (score == 0.0) {
      result.setCategory("Exact");
    } else if (score == Double.MAX_VALUE) {
      if (result.getBuyer() == null)
        result.setCategory("Only in Supplier");

      if (result.getSeller() == null)
        result.setCategory("Only in Buyer");
    } else
      result.setCategory("Partial");
  }
}
