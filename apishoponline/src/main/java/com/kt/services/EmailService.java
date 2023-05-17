package com.kt.services;

public interface EmailService {
    void sendMail(String toEmail, String subject, String body);
}
