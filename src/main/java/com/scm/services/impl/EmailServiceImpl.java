package com.scm.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.scm.services.EmailService;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender eMailSender;

    @Value("${spring.mail.properties.email_from}") // Read from environment variable
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail); // Use the configured "from" email
        eMailSender.send(message);
    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = eMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true indicates HTML
        helper.setFrom(fromEmail); // Use the configured "from" email
        eMailSender.send(message);
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, File attachment) throws MessagingException {
        MimeMessage message = eMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom(fromEmail); // Use the configured "from" email
        helper.addAttachment("Invoice", attachment);
        eMailSender.send(message);
    }
}