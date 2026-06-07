package com.mibarrio.api.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    @Override
    public boolean enviarEmail(String destination, String subject, String textMessage) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(destination);
            if (fromAddress != null && !fromAddress.isBlank()) {
                helper.setFrom(fromAddress);
            }
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            return true;
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
