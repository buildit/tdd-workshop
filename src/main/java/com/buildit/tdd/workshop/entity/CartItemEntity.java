package com.buildit.tdd.workshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="cartitem")
public class CartItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long cartItemId;
	private long productId;
	private byte productQty;
	private long cartId;
}
