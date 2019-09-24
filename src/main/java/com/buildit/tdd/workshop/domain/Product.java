package com.buildit.tdd.workshop.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private long productId;
	private String name;
	private String manufacturer;
	private double price;
	private String category;
	private boolean isReturnable;
	private int numDaysTillReturnable;
	private int stockQuantity;
	
}
