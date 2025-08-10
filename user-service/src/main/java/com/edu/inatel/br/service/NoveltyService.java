package com.edu.inatel.br.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edu.inatel.br.dto.ProductDTO;
import com.edu.inatel.br.dto.PromotionMessageDTO;
import com.edu.inatel.br.model.Novelty;
import com.edu.inatel.br.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class NoveltyService {
    private static final String USERS_COLLECTION = "users";
    private static final String NOVELTIES_COLLECTION = "novelties";

    public void createNoveltyForInterestedUsers(PromotionMessageDTO promotion) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            List<String> promotionCategories = (promotion.getProducts() == null) ? Collections.emptyList() :
                promotion.getProducts().stream()
                        .filter(Objects::nonNull)      
                        .map(ProductDTO::getCategory)   
                        .filter(Objects::nonNull)     
                        .map(String::toLowerCase)    
                        .distinct()                    
                        .collect(Collectors.toList());

            if (promotionCategories.isEmpty()) return;

            // Encontra usuários que têm alguma das categorias da promoção em suas preferências
            ApiFuture<QuerySnapshot> future = db.collection(USERS_COLLECTION)
                    .whereArrayContainsAny("preferredCategories", promotionCategories)
                    .get();

            List<User> interestedUsers = future.get().toObjects(User.class);

            for (User user : interestedUsers) {
                Novelty novelty = new Novelty();
                novelty.setUserId(user.getEmail());
                novelty.setPromotionId(promotion.getId());
                novelty.setMessage("Nova promoção que pode te interessar: " + promotion.getDescription());
                novelty.setRead(false);
                db.collection(NOVELTIES_COLLECTION).add(novelty);
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar novidade: " + e.getMessage());
        }
    }

    public List<Novelty> findByUser(String userId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(NOVELTIES_COLLECTION)
                .whereEqualTo("userId", userId).get();
        return future.get().toObjects(Novelty.class);
    }
}