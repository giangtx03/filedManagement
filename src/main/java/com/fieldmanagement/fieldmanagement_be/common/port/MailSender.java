package com.fieldmanagement.fieldmanagement_be.common.port;

import jakarta.mail.MessagingException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface MailSender {
    CompletableFuture<String> sendEmailAsync(
            String to, String subject, String template, Map<String, Object> variables
    ) throws MessagingException;
}
