package com.ibus.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtpEmail(String toMailId, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); 
        message.setTo(toMailId);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

}
