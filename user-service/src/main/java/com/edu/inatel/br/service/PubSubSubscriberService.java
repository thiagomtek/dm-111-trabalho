package com.edu.inatel.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.edu.inatel.br.dto.PromotionMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;

@Service
public class PubSubSubscriberService {

    @Autowired
    private NoveltyService noveltyService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void receiveMessage(String payload,
                               @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        System.out.println("Mensagem recebida: " + payload);
        try {
            // Deserializa o payload JSON para o nosso DTO
            PromotionMessageDTO promotion = objectMapper.readValue(payload, PromotionMessageDTO.class);
            noveltyService.createNoveltyForInterestedUsers(promotion);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem do Pub/Sub: " + e.getMessage());
        }
        message.ack(); // Confirma o recebimento da mensagem
    }
}