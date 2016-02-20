package org.apache.airavata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
	
	private String email = "";
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private String to = "";
	private String from = "";
	private String host = "";
    private Properties props = new Properties();
    private Session session = Session.getDefaultInstance(props, null);
	
	public Email(String email){
		this.email = email;
		pattern = Pattern.compile(EMAIL_PATTERN); 
	}
	
//	public Email(String email, String to, String from, String host){
//		this.email = email;
//		this.to = to;
//		this.from = from;
//		this.host = host;
//	    	props.setProperty("mail.imap.ssl.enable", "true");
//	    	props.put("mail.smtp.host", "smtp.gmail.com");
//		    props.put("mail.smtp.socketFactory.port", 465);
//		    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		    props.put("mail.smtp.auth", "true");
//		    props.put("mail.smtp.port", "465");
//		pattern = Pattern.compile(EMAIL_PATTERN); 
//	}
	
	public boolean validateEmail(String email){
			matcher = pattern.matcher(email);
			return (email.substring(email.length()-3, email.length()).equals("edu") ||
					email.substring(email.length()-3, email.length()).equals("gov")) &&
					correctPattern(email);
	}
	
//	public void sendEmail(String subject, String body){
//		try{
//			Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
//			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			msg.setSubject(subject);
//			msg.setText(body);
//			Transport.send(msg);
//			System.out.println("Message Sent. ");
//			}
//		catch(Exception e){e.printStackTrace();}
//	}
	
	private boolean correctPattern(String email){
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	

}
