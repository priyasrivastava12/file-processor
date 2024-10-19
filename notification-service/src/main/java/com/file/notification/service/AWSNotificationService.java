package com.file.notification.service;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.file.notification.config.AWSClientConfig;
import com.file.notification.exception.NotificationException;
import com.file.notification.model.FileResultMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AWSNotificationService {

  private AmazonSNS amazonsns;

  private AWSClientConfig awsClientConfig;


  @Autowired
  public AWSNotificationService(AWSClientConfig awsClientConfig) {
    this.awsClientConfig = awsClientConfig;
    this.amazonsns = awsClientConfig.amazonSNS();
  }

  public void notifyCustomer(FileResultMetaData notificationMetaData) {

    String subject = "File Uploaded Notification";
    String message = "The file has been successfully uploaded. You can access it using the following link:\n" +
            notificationMetaData.getOutputfilePath();

    try {
      System.out.println(message);
    } catch (Exception e) {
      throw new NotificationException("Error sending notification", e);
    }

  }

  private static void sendEmailNotification(AmazonSNS snsClient, String topicArn, String subject, String message, String recipientEmail) {
    PublishRequest publishRequest = new PublishRequest(topicArn, message, subject);
    PublishResult publishResult = snsClient.publish(publishRequest);
    System.out.println("Message sent. MessageId: " + publishResult.getMessageId());
  }

}
