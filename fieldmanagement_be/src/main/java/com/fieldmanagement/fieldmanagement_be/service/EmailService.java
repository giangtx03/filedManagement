package com.fieldmanagement.fieldmanagement_be.service;

import jakarta.mail.MessagingException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface EmailService {
     CompletableFuture<String> sendEmailAsync(String to, String subject, String template, Map<String, Object> variables)
             throws MessagingException;
}
