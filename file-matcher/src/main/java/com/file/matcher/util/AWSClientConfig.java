package com.file.matcher.util;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSClientConfig {

  @Value("${aws.accessKey}")
  private String accessKey;

  @Value("${aws.secretAccessKey}")
  private String secretAccessKey;

  @Value("${aws.bucketName}")
  private String bucketName;

  private final Region region = Region.US_East_2; //kendi region bilginiz ile değiştirilmeli

  @Bean
  public AmazonS3 amazonS3() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);

    return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(region.toString())
            .build();
  }

}
