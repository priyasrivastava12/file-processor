package com.file.matcher.model;

import java.util.Objects;

public class DataKey {

  String key;

  public DataKey(String key) {
    this.key = key;
  }

  public DataKey() {
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataKey dataKey = (DataKey) o;
    return dataKey.getKey().equals(key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }
}
