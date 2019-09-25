package com.buildit.tdd.workshop.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart")
public class CartEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long cartId;
	@OneToMany(mappedBy = "cartId")
	private List<CartItemEntity> cartItemList;
	private long customerId;
}
