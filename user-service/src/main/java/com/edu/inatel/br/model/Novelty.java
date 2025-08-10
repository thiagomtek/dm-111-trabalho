package com.edu.inatel.br.model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Novelty {
	@DocumentId
	private String id;
	private String userId;
	private String message;
	private String promotionId;
	private boolean read;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}