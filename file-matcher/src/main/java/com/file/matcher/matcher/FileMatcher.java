package com.file.matcher.matcher;

import com.file.matcher.calculationStrategy.ScoreStrategyUtil;
import com.file.matcher.exception.KafkaPublisherException;
import com.file.matcher.model.*;
import com.file.matcher.service.KafkaPublisherService;
import com.file.matcher.util.ThresholdConfig;
import com.file.matcher.validator.DataValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FileMatcher {

  @Autowired
  ThresholdConfig thresholdConfig;

  public Result match(Row buyer, List<Row> suppliers, int threshold) {
    Double minScore = Double.MAX_VALUE;
    Row supplierResult = null;
    Result result = new Result();

    for (Row supplier : suppliers) {
      Double score = calculateScore(buyer, supplier, threshold);
      if (score < minScore) {
        minScore = score;
        supplierResult = supplier;
        result.setSupplierIndex(suppliers.indexOf(supplier));
      }
    }
    result.setBuyer(buyer);
    result.setSeller(supplierResult);
    result.setScore(minScore);
    return result;
  }

  private Double calculateScore(Row buyer, Row supplier, int threshold) {
    Map<DataKey, DataValue> brow = buyer.getRow();
    Map<DataKey, DataValue> srow = supplier.getRow();

    Double finalscore = 0.0;

    for (DataKey key : brow.keySet()) {
      String bvalue = brow.get(key).getValue();
      String svalue = srow.get(key).getValue();
      DataType dataType = brow.get(key).getDataType();

      if (bvalue == null && svalue == null) {
        continue;
      }

      boolean isValid = DataValidatorUtil.getInstance(dataType).validate(bvalue, svalue, thresholdConfig.getThreshold(dataType));
      if (!isValid) {
        finalscore = Double.MAX_VALUE;
        break;
      } else {
        finalscore = finalscore + ScoreStrategyUtil.getInstance(dataType).calculateScore(bvalue, svalue);
      }
    }
    return finalscore;

  }


}