package com.example.demo.project_emp_meetings;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.catalina.Service;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;


@org.springframework.stereotype.Service
public class GMailer {
	
	@Autowired
	JdbcTemplate jtemp;
	
	 private static final String from_user_email_test = "xxxxxxxxx@gmail.com";
//	 private static final String to_user_email_test = "xxxxxxxxx@gmail.com";
	 private static Gmail service;
	
	
	public GMailer() throws Exception {
		 // Build a new authorized API client service.
		    NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		    // GsonFactory is used to convert java object to JSON object
		    GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
		    // loading credentials
		    service = new Gmail.Builder(HTTP_TRANSPORT, jsonFactory, getCredentials(HTTP_TRANSPORT, jsonFactory))
		        .setApplicationName("SpringBoot Desktop client 1")  // project name in GmailApi cloud
		        .build();
		   
	 }
	

	private static com.google.api.client.auth.oauth2.Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, GsonFactory jsonFactory)
			throws IOException {
		
		    // Load client secrets.
		    InputStream in = GMailer.class.getResourceAsStream("/client_secret_575886744383-rotk2puuofrg9onavq3q28j4ghdlpu7v.apps.googleusercontent.com.json");
		    if (in == null) {
		      throw new FileNotFoundException("Resource not found: " + in);
		    }
		    GoogleClientSecrets clientSecrets =
		        GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

		    // Build flow and trigger user authorization request.
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		    		// builder takes HTTP transport, Java to JSON object i.e., jsonFactory, Scope i.e., action like send email and more.
		        HTTP_TRANSPORT, jsonFactory, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
		        .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
		        .setAccessType("offline")
		        .build();
		    // build a local instance for us with the given port 
		    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//		    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		    // finally generate a URL for the user authentication and returns an authorized Credential object.
		    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	
	public void SendMail(String subject, String message, String employee_content_ids) throws Exception{
		System.out.println("entered--- je");
		getCredentials(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance());
		System.out.println(employee_content_ids+"??????????");
		
	    // Create the email content
		// employee id's of there respective team only
		String[] employee_content_id=employee_content_ids.split(" ");
		
		for(String id:employee_content_id) {
			String employee_content_ids_sql = "select * from employee where id=?";
			List<Map<String,Object>>list =jtemp.queryForList(employee_content_ids_sql,id);
			//getting the email of the respective employee based on employee id
			for(Map list_obj : list) {
				// sending the email to each employee 
				String emp_email_id = (String)list_obj.get("email");
				
				
				 // Encode as MIME message
			    Properties props = new Properties();
			    Session session = Session.getDefaultInstance(props, null);
			    MimeMessage email = new MimeMessage(session);
			    // from email address
			    email.setFrom(new InternetAddress(from_user_email_test));
			    // to-email address
			    email.addRecipient(javax.mail.Message.RecipientType.TO,
			    		new InternetAddress(emp_email_id));  
			    // email subject
			    email.setSubject(subject);
			    // email body
			    email.setText(message);
			    
			    

			    // Encode and wrap the MIME message into a gmail message
			    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			    email.writeTo(buffer);
			    byte[] rawMessageBytes = buffer.toByteArray();
				String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
			    Message msg = new Message();
			    msg.setRaw(encodedEmail);
			    
			    try {
			    System.out.println("entered");
			      msg = ((Gmail) service).users().messages().send("me",msg).execute();
			      System.out.println("entered exit");
			      System.out.println("Draft id: " + msg.getId());	
			      System.out.println(msg.toPrettyString());
//			      return draft;
			    } catch (GoogleJsonResponseException e) {
			      // TODO(developer) - handle error appropriately
			      GoogleJsonError error = e.getDetails();
			      if (error.getCode() == 403) {
			        System.err.println("Unable to create draft: " + e.getDetails());
			      } else {
			        throw e;
			      }
			    }

			}
		}

	}
	
//	public static void main(String args[]) throws Exception{
//		new GMailer().SendMail("Meating for ITG-164 ", 
//				"""
//				Hello Good afternoon.
//				
//				The following the meeting link for https://link 
//				for session on revise topics of CORE JAVA.
//				
//				Thank You.
//				""");
//	}
}
