package com.erp.Ecommeres.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.erp.Ecommeres.dto.MailBody;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailBody) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailBody.getTo());
        message.setFrom("sujatadhere24@gmail.com");

        message.setSubject(mailBody.getSubject());
        message.setText(mailBody.getText());

        javaMailSender.send(message);
    }
}
