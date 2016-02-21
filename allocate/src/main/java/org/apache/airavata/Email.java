package org.apache.airavata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
	
	private String email = "";
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public Email(String email){
		this.email = email;
		pattern = Pattern.compile(EMAIL_PATTERN); 
	}
	

	public boolean validateEmail(String email){
			matcher = pattern.matcher(email);
			return (email.substring(email.length()-3, email.length()).equals("edu") ||
					email.substring(email.length()-3, email.length()).equals("gov")) &&
					correctPattern(email);
	}
		
	private boolean correctPattern(String email){
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	

}