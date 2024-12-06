package com.example.demoImage.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.internet.MimeMessage;

@RestController
public class EmailController {
	@Autowired
	private JavaMailSender mailsender;
	
	
	
	public EmailController(JavaMailSender mailsender) {
		this.mailsender = mailsender;
	}


	/*
	@RequestMapping("/send-email")
	public String sendEmail() {
		try {
		SimpleMailMessage msg= new SimpleMailMessage();
		
		msg.setFrom("nimmagaddakavya5@gmail.com");
		msg.setTo("kavyanimmagadda12@gmail.com");
		msg.setSubject("Simple test email from Admin!..");
		msg.setText("This is a sample email body for my 1st email");
		
		mailsender.send(msg);
		return "success";
		}catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
		 
	}
	
	*/
	
	
	@RequestMapping("/send-email-with-attachment")
	public String sendEmail() {
		try {
			
			MimeMessage msg = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			
			
			helper.setFrom("nimmagaddakavya5@gmail.com");
			helper.setTo("kavyanimmagadda12@gmail.com");
			helper.setSubject("Java email from with attachment Admin!..");
			helper.setText("This is a sample email body for my 1st email .Please find the attached documents below.");
			helper.addAttachment("passport photo.jpg", new File("C:\\Users\\user\\Downloads\\kavya important\\passport photo.jpg"));
			helper.addAttachment("CO1_4.ppt", new File("C:\\Users\\user\\Downloads\\cis\\co1\\CO1_4.ppt"));
			
			mailsender.send(msg);
			return "success";
			
		}catch (Exception e) {
			
			return e.getMessage();
		}
		 
	}
	

}
