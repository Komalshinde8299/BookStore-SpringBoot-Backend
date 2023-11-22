package com.bookStore.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MyEmail {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	SimpleMailMessage mailMsg;
	
	
	public void sendMail(String to, String subject, String massage) {
		mailMsg.setTo(to);
		mailMsg.setSubject(subject);
		mailMsg.setText(massage);
		mailSender.send(mailMsg);
		
	}
	
	

}
