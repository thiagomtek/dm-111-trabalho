package com.edu.inatel.br.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edu.inatel.br.model.Restaurant;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class RestaurantService {

    private static final String COLLECTION_NAME = "restaurants";

    public Restaurant createRestaurant(Restaurant restaurant) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.DocumentReference> future = db.collection(COLLECTION_NAME).add(restaurant);
        restaurant.setId(future.get().getId());
        return restaurant;
    }
    
    public List<Restaurant> getRestaurantsByUserId(String userId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).whereEqualTo("userId", userId).get();
        return future.get().getDocuments().stream()
                .map(doc -> doc.toObject(Restaurant.class))
                .collect(Collectors.toList());
    }
}