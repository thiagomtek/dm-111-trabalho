package com.edu.inatel.br.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Acl.User;
import com.google.firebase.cloud.FirestoreClient;

public class UserService {

	public User findUserByEmail(String email) {

		try {
			Firestore db = FirestoreClient.getFirestore();
			CollectionReference usersCollection = db.collection("users");

			Query query = usersCollection.whereEqualTo("email", email);
			ApiFuture<QuerySnapshot> querySnapshot = query.get();

			if (querySnapshot.get().isEmpty()) {
				throw new RuntimeException("Usuário não encontrado com o e-mail: " + email);
			}

			return querySnapshot.get().getDocuments().get(0).toObject(User.class);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar usuário no Firebase", e);
		}
	}
}