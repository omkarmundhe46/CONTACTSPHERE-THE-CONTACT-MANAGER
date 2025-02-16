package com.scm.services;

import jakarta.mail.MessagingException;

import java.io.File;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml(String to, String subject, String htmlBody) throws MessagingException;

    void sendEmailWithAttachment(String to, String subject, String body, File attachment) throws MessagingException;
}

