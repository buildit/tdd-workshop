package com.buildit.tdd.workshop.domain;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	private long productId;
	private byte productQty;
	private long cartId;	
}
