package com.edu.inatel.br.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.inatel.br.model.Promotion;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class PromotionService {

    private static final String COLLECTION_NAME = "promotions";
    
    @Autowired
    private PubSubPublisherService publisherService;
    
    public Promotion createPromotion(Promotion promotion) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).add(promotion).get();
        promotion.setId(docRef.getId());
 
        publisherService.publishNewPromotion(promotion);
        
        return promotion;
    }
    
    public List<Promotion> getPromotionsByRestaurant(String restaurantId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).whereEqualTo("restaurantId", restaurantId).get();
        return future.get().getDocuments().stream()
                .map(doc -> doc.toObject(Promotion.class))
                .collect(Collectors.toList());
    }
   
    public Promotion updatePromotion(String id, Promotion promotion) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).document(id).set(promotion).get();
        return promotion;
    }
    
    public void deletePromotion(String id) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).document(id).delete();
    }
    
    public List<Promotion> getAllPromotions() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        return future.get().getDocuments().stream()
                .map(doc -> doc.toObject(Promotion.class))
                .collect(Collectors.toList());
    }

}