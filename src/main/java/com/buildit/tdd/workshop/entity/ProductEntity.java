package com.buildit.tdd.workshop.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String name;
	private String manufacturer;
	private double price;
	private String category;
	private boolean isReturnable;
	private int numDaysTillReturnable;
	private int stockQuantity;
	
}
