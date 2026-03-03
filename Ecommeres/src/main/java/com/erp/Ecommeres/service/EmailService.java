package com.erp.Ecommeres.service;

import com.erp.Ecommeres.dto.MailBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.url}")
    private String brevoUrl;

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendSimpleMessage(MailBody mailBody) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);

        Map<String, Object> payload = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("email", senderEmail);
        sender.put("name", senderName);

        Map<String, String> toUser = new HashMap<>();
        toUser.put("email", mailBody.getTo());

        payload.put("sender", sender);
        payload.put("to", new Object[]{toUser});
        payload.put("subject", mailBody.getSubject());

        // You can use htmlContent also
        payload.put("htmlContent", "<h2>" + mailBody.getText() + "</h2>");

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(brevoUrl, request, String.class);

            System.out.println("Brevo Response: " + response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Brevo API call failed");
        }
    }
}