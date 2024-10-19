package com.file.notification.util;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {

  public static void main(String[] args) {

    System.out.println("SimpleEmail Start");

    String smtpHostServer = "smtp.example.com";
    String emailID = "piyushtek@gmail.com";

    Properties props = System.getProperties();

    props.put("mail.smtp.host", smtpHostServer);

    Session session = Session.getInstance(props, null);

    EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
  }

  public static void sendEmail(Session session, String toEmail, String subject, String body){
    try
    {
      MimeMessage msg = new MimeMessage(session);
      //set message headers
      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
      msg.addHeader("format", "flowed");
      msg.addHeader("Content-Transfer-Encoding", "8bit");

      msg.setFrom(new InternetAddress("priyasrivastava18official@gmail.com", "NoReply-JD"));

      msg.setReplyTo(InternetAddress.parse("piyushtek@gmail.com", false));

      msg.setSubject(subject, "UTF-8");

      msg.setText(body, "UTF-8");

      msg.setSentDate(new Date());

      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
      System.out.println("Message is ready");
      Transport.send(msg);

      System.out.println("EMail Sent Successfully!!");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
