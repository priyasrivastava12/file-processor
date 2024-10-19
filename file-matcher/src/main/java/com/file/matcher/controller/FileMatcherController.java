package com.file.matcher.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.file.matcher.parser.CsvDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
public class FileMatcherController {


  @Autowired
  AmazonS3 amazonS3;
  @Value("${aws.bucketName}")
  private String bucketName;


  @Autowired
  CsvDataParser csvDataParser;

  @GetMapping("/processFiles")
  private void processFile(@RequestParam String buyerFile, @RequestParam String supplierFile, @RequestParam String taskID) throws IOException {

    // Create a File object from the MultipartFile

    // Upload the file to S3
    S3Object buyers3Object = amazonS3.getObject(new GetObjectRequest(bucketName, buyerFile));
    S3ObjectInputStream buyerStream = buyers3Object.getObjectContent();

    File buyerTempFile = convertInputStreamToFile(buyerStream, "buyerFile");
    S3Object suppliers3Object = amazonS3.getObject(new GetObjectRequest(bucketName, supplierFile));
    S3ObjectInputStream supplierStream = suppliers3Object.getObjectContent();
    File supplierTempFile = convertInputStreamToFile(supplierStream, "supplierFile");

    csvDataParser.processFile(buyerTempFile, supplierTempFile, taskID);
    buyerTempFile.deleteOnExit();
    supplierTempFile.deleteOnExit();

  }

  private static File convertInputStreamToFile(InputStream inputStream, String fileName) throws IOException {
    Path tempFile = Files.createTempFile(fileName, "");
    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
    File outputFile = tempFile.toFile();
    return outputFile;
  }

}
