package com.buildit.tdd.workshop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class CartEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long cartId;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cartId")
	private List<CartItemEntity> cartItemList;
	@Column(unique = true)
	private long customerId;
}
