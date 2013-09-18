package com.example.runspyrun;

//File Name SendMail.java

/*
 *	SendMail.java
 *	@author Crouching Tiger Running Spy
 *
 *  A java class that is used to sending an email as the user click on "forgot password?" in login page
 *  The email is sent from our gmail (RunSpyRun Team<runspyrun7@gmail.com>). This class was created separately with another classes,
 *  that's why when it's combined to others, this class seems can't work properly because of this is not 
 *  an android java version.
 *  
 *  It needs external JAR: activation.jar and javax.mail.jar
 */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail
{
public static void main(String [] args)
{    
	
   // Recipient's email ID needs to be mentioned.
   // Emails from Database go in here
   String to = "julia.edisa@gmail.com";

   // Sender's email ID needs to be mentioned
   String from = "RunSpyRun Team<runspyrun7@gmail.com>";

   // Assuming you are sending email from localhost
   String host = "localhost";

   // Get system properties
   Properties properties = System.getProperties();

   // Setup mail server
// Setup mail server
   properties.setProperty("mail.smtp.host", "smtp.gmail.com");
   properties.setProperty("mail.smtp.auth", "true");
   properties.setProperty("mail.smtp.starttls.enable","true");
   properties.setProperty("mail.debug", "true");
   properties.setProperty("mail.smtp.port", "587");
   // Get session
   //Session session = Session.getDefaultInstance(props, null);
   Session session = Session.getDefaultInstance(properties,
           new javax.mail.Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication("runspyrun7@gmail.com", "DECO3801");
               }
           });

   try{
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO,
                               new InternetAddress(to));

      // Set Subject: header field
      message.setSubject("Forgotten Password");

      // Now set the actual message
      // Insert user accounts and passwords for email
      message.setText("This email has been sent because you have forgotten your RunSpyRun " +
      		"password and need help remembering. The password for account: User1 is: SpyingIsAwesome");

      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
   }catch (MessagingException mex) {
      mex.printStackTrace();
   }
}
}