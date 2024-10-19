package com.file.notification.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
public class AWSClientConfig {


  private String accessKey;


  private String secretAccessKey;


  private String bucketName;

  private String topicArn;


  @Bean
  public AmazonSNS amazonSNS() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);

    AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
            .withRegion(Regions.US_EAST_2.getName())
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    return snsClient;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretAccessKey() {
    return secretAccessKey;
  }

  public void setSecretAccessKey(String secretAccessKey) {
    this.secretAccessKey = secretAccessKey;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }


  public String getTopicArn() {
    return topicArn;
  }

  public void setTopicArn(String topicArn) {
    this.topicArn = topicArn;
  }
}
