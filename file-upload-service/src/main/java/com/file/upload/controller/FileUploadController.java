package com.file.upload.controller;

import com.file.upload.dao.UserMetaDataRepository;
import com.file.upload.model.FileCompareMetaData;
import com.file.upload.model.FileCompareMetaDataFactory;
import com.file.upload.service.AWSFileUploadService;
import com.file.upload.service.KafkaPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploadController {

  private final AWSFileUploadService awsFileUploadService;

  private final KafkaPublisherService kafkaPublisherService;


  private final UserMetaDataRepository userMetaDataRepository;

  @Autowired
  public FileUploadController(AWSFileUploadService awsFileUploadService, KafkaPublisherService kafkaPublisherService, UserMetaDataRepository userMetaDataRepository) {
    this.awsFileUploadService = awsFileUploadService;
    this.kafkaPublisherService = kafkaPublisherService;
    this.userMetaDataRepository = userMetaDataRepository;
  }

  @PostMapping("/uploadFiles")
  public ResponseEntity<String> handleFileUpload(@RequestParam("userName") String userName,
                                                 @RequestParam("mobileNumber") String mobileNumber,
                                                 @RequestParam("emailId") String emailId,
                                                 @RequestParam("file1") MultipartFile file1,
                                                 @RequestParam("file2") MultipartFile file2) throws IOException {

    String key = userName + mobileNumber;
    try {
      FileCompareMetaData fileCompareMetaData = createFileCompareMetaData(key, file1,
              file2, userName, mobileNumber, emailId);

      awsFileUploadService.uploadFilesToS3(file1, file2, key, fileCompareMetaData);
      kafkaPublisherService.publishEvent(fileCompareMetaData);
      userMetaDataRepository.saveFileCompareMetaData(fileCompareMetaData);
      return ResponseEntity.ok("Files uploaded successfully. Your request is in process. " +
              "You will be receiving the matched output file link on your email ID soon!");
    } catch (Exception e) {
      // other exceptions
      return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
              "Error processing the request: " + e.getMessage());
    }
  }

  private FileCompareMetaData createFileCompareMetaData(String key, MultipartFile file1,
                                                        MultipartFile file2,
                                                        String userName,
                                                        String mobileNumber,
                                                        String emailId) {
    return FileCompareMetaDataFactory.createFileCompareMetaData
            (key, file1, file2, userName, mobileNumber, emailId);

  }

  private ResponseEntity<String> createErrorResponse(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(message);
  }


}
