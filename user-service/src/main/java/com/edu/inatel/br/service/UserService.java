package com.edu.inatel.br.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edu.inatel.br.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class UserService {
	private static final String COLLECTION_NAME = "users";

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User createUser(User user) throws ExecutionException, InterruptedException {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(user.getEmail()).set(user);
		future.get();
		return user;
	}

	public User findUserByEmail(String email) throws ExecutionException, InterruptedException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection(COLLECTION_NAME).document(email);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			return document.toObject(User.class);
		} else {
			throw new RuntimeException("Usuário não encontrado");
		}
	}

	public User updatePreferredCategories(String userId, List<String> categories)
			throws ExecutionException, InterruptedException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection(COLLECTION_NAME).document(userId);
		docRef.update("preferredCategories", categories).get();
		return findUserByEmail(userId);
	}
}