package com.file.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.file.upload.model.FileCompareMetaData;
import com.file.upload.util.AWSClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AWSFileUploadService {

  private AmazonS3 amazonS3;

  private AWSClientConfig awsClientConfig;

  //private FileMetaDataRepository fileMetaDataRepository;


  @Autowired
  public AWSFileUploadService(AWSClientConfig awsClientConfig)
                              //,FileMetaDataRepository fileMetaDataRepository)
  {
    this.awsClientConfig = awsClientConfig;
    //this.fileMetaDataRepository = fileMetaDataRepository;
    this.amazonS3 = awsClientConfig.amazonS3();
  }

  public void uploadFilesToS3(MultipartFile file1, MultipartFile file2, String key,
                              FileCompareMetaData fileCompareMetaData) throws IOException {
    uploadFileToS3(file1, key);
    uploadFileToS3(file2, key);
    //save data to DB
    //fileMetaDataRepository.saveFileCompareMetaData(fileCompareMetaData);
  }
  private void uploadFileToS3(MultipartFile multipartFile, String key) throws IOException {
    File file = convertMultipartFileToFile(multipartFile);
    // Upload the file to S3
    amazonS3.putObject(new PutObjectRequest(awsClientConfig.getBucketName(),
            key + "/" + file.getName(), file));
  }


  private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
    File file = new File(multipartFile.getOriginalFilename());
    multipartFile.transferTo(file);
    return file;
  }


}
