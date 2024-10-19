package com.file.matcher.model;

import java.util.HashMap;
import java.util.Map;

public class Row {
  Map<DataKey , DataValue> row;

  public Row() {
    row=new HashMap<>();
  }

  public Map<DataKey, DataValue> getRow() {
    return row;
  }

  public void setRow(Map<DataKey, DataValue> row) {
    this.row = row;
  }
}

