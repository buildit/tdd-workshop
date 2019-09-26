package com.buildit.tdd.workshop.domain;


import com.buildit.tdd.workshop.entity.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	private long cartId;
	private List<CartItemEntity> cartItemList;
	private long customerId;
}
