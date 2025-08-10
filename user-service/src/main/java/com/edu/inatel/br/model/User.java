package com.edu.inatel.br.model;

import com.google.cloud.firestore.annotation.DocumentId;
import java.util.List;

public class User {
	@DocumentId
	private String id;
	private String email;
	private String password;
	private List<String> preferredCategories;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getPreferredCategories() {
		return preferredCategories;
	}

	public void setPreferredCategories(List<String> preferredCategories) {
		this.preferredCategories = preferredCategories;
	}
}