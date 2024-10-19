package com.file.matcher.model;

public class Result {

  Row buyer;
  String category;

  int supplierIndex;
  Double score;
  Row seller;

  public Result() {
    supplierIndex = -1;
  }

  public int getSupplierIndex() {
    return supplierIndex;
  }

  public void setSupplierIndex(int supplierIndex) {
    this.supplierIndex = supplierIndex;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public Row getBuyer() {
    return buyer;
  }

  public void setBuyer(Row buyer) {
    this.buyer = buyer;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Row getSeller() {
    return seller;
  }

  public void setSeller(Row seller) {
    this.seller = seller;
  }
}
