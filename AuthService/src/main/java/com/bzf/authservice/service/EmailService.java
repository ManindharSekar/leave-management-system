package com.bzf.authservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }



	public String sendEmail(String userName, String email, String confirmationToken) {
		// TODO Auto-generated method stub
		try {
			String appURL="http://localhost:8080";
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom("m121027001@gmail.com");
			mailMessage.setTo(email);
			mailMessage.setSubject("Registration Confirmation");
			mailMessage.setText("Hi "+userName+",\n To confirm your e-mail address, please click the link below:\n"
					+ appURL + "/confirm?token=" + confirmationToken);
			mailSender.send(mailMessage);
			return "Success";
			
			}catch(Exception e) {
				return e.getMessage();
			}
	}

}
