package com.file.notification.model;

public class FileResultMetaData {

  String outputfilePath;

  String taskId;
  String userName;

  String emailId;

  String mobileNumber;

  public String getOutputfilePath() {
    return outputfilePath;
  }

  public void setOutputfilePath(String outputfilePath) {
    this.outputfilePath = outputfilePath;
  }

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

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
}
