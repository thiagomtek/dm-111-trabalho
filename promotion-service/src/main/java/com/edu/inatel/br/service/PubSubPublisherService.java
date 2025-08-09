package com.edu.inatel.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.edu.inatel.br.model.Promotion;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;

@Service
public class PubSubPublisherService {

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Value("${gcp.pubsub.topic-name}")
    private String topicName;

    public void publishNewPromotion(Promotion promotion) {
        System.out.println("Publicando promoção com ID: " + promotion.getId() + " no tópico: " + topicName);
        ListenableFuture<String> future = (ListenableFuture<String>) this.pubSubTemplate.publish(topicName, promotion);
        
        future.addCallback(
            result -> System.out.println("Mensagem publicada com sucesso: " + result),
            ex -> System.err.println("Erro ao publicar mensagem: " + ex.getMessage())
        );
    }
}