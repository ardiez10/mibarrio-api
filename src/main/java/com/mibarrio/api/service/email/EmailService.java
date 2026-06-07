package com.mibarrio.api.service.email;

public interface EmailService {

    boolean enviarEmail(String destination, String subject, String textMessage);
}
