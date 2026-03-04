package com.vitorbnr.wallet.service;

import com.vitorbnr.wallet.domain.user.User;
import com.vitorbnr.wallet.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Erro ao enviar notificação para o usuário");
                throw new Exception("Serviço de notificação fora do ar");
            }
        } catch (Exception e) {
            System.out.println("Falha na comunicação com serviço de email: " + e.getMessage());
        }
    }
}