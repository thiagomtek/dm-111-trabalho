package com.edu.inatel.br.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionMessageDTO {
	private String id;
	private String description;
	private List<ProductDTO> products;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
}