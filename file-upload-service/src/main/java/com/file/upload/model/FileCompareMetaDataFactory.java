package com.file.upload.model;

import org.springframework.web.multipart.MultipartFile;

public class FileCompareMetaDataFactory {

  public static FileCompareMetaData createFileCompareMetaData(String key, MultipartFile file1, MultipartFile file2, String userName, String mobileNumber, String emailId) {
    FileCompareMetaData fileCompareMetaData = new FileCompareMetaData();
    fileCompareMetaData.setBuyerFilePath(key + "/" + file1.getOriginalFilename());
    fileCompareMetaData.setSupplierFilePath(key + "/" + file2.getOriginalFilename());
    fileCompareMetaData.setMobileNumber(mobileNumber);
    fileCompareMetaData.setEmailId(emailId);
    fileCompareMetaData.setUserName(userName);
    return fileCompareMetaData;
  }
}
