package com.file.matcher.util;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import com.file.matcher.model.DataType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThresholdConfig {

  @Value("${dateThreshold}")
  public int dateThreshold;

  @Value("${numberThreshold}")
  public int numberThreshold;


  @Value("${stringThreshold}")
  public int stringThreshold;

  public int getThreshold(DataType dataType) {
    if (dataType.equals(DataType.NUMERIC)) {
      return numberThreshold;
    }
    if (dataType.equals(DataType.DATE)) {
      return dateThreshold;
    }
    if (dataType.equals(DataType.STRING)) {
      return stringThreshold;
    }
    return 0;
  }

}
