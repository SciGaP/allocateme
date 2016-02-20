package org.apache.airavata;

public class Email {
	
	private String email = "";
	
	public Email(String email){
		this.email = email;
	}
	
	public boolean validateEmail(String email){
			return email.substring(email.length()-3, email.length()).equals("edu") ||
					email.substring(email.length()-3, email.length()).equals("gov"); 
	}

}
