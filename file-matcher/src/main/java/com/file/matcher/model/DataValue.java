package com.file.matcher.model;

public class DataValue {

  String value;
  DataType dataType;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }



  @Override
  public String toString() {
    return value;
  }
}
