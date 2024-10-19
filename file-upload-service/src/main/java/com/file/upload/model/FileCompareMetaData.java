package com.file.upload.model;

public class FileCompareMetaData {
  String buyerFilePath;
  String supplierFilePath;

  String userName;

  String emailId;

  String mobileNumber;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getBuyerFilePath() {
    return buyerFilePath;
  }

  public void setBuyerFilePath(String buyerFilePath) {
    this.buyerFilePath = buyerFilePath;
  }

  public String getSupplierFilePath() {
    return supplierFilePath;
  }

  public void setSupplierFilePath(String supploerFilePath) {
    this.supplierFilePath = supploerFilePath;
  }
}
