package com.buildit.tdd.workshop.service;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.domain.CartItem;
import com.buildit.tdd.workshop.entity.CartEntity;
import com.buildit.tdd.workshop.entity.CartItemEntity;
import com.buildit.tdd.workshop.exception.CartItemNotCreatedException;
import com.buildit.tdd.workshop.exception.CartItemNotFoundException;
import com.buildit.tdd.workshop.repository.CartItemRepository;
import com.buildit.tdd.workshop.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceTest {

	@InjectMocks
	private CartItemService cartItemService;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private CartRepository cartRepository;
	
	@Mock
	private CartItemRepository cartItemRepository;
	
	private CartEntity cartEntity;
	private Cart cart;
	private CartItemEntity cartItemEntity;
	private CartItem cartItem;
	private final long cartId = 1;

	@Before
	public void setup() {
		cartEntity = cartEntity.builder().cartId(1).customerId(1).build();
		cart = cart.builder().cartId(1).customerId(1).build();
		cartItemEntity = cartItemEntity.builder().cartId(cartEntity.getCartId()).productId(1).productQty((byte) 1).build();
		cartItem = cartItem.builder().cartId(cartEntity.getCartId()).productId(1).productQty((byte) 1).build();
	}
	
	@Test
	public void createCartItem_shouldCreate_successfully() throws CartItemNotCreatedException {	
		when(objectMapper.convertValue(cartItem, CartItemEntity.class)).thenReturn(cartItemEntity);
		when(cartItemRepository.save(Mockito.any(CartItemEntity.class))).thenReturn(cartItemEntity);
		when(cartRepository.findById(cartId)).thenReturn(Optional.ofNullable(cartEntity));
		when(objectMapper.convertValue(cartEntity, Cart.class)).thenReturn(cart);
		
		Cart response = cartItemService.createItem(cartId, cartItem);
		assertEquals(cart, response);
	}
	
//	@Test
//	public void updateCartItem_shouldUpdate_cartItem() throws CartItemNotFoundException {
//		when(objectMapper.convertValue(cartItem, CartItemEntity.class)).thenReturn(cartItemEntity);
//		when(cartItemRepository.save(Mockito.any(CartItemEntity.class))).thenReturn(cartItemEntity);
//		when(cartRepository.findById(cartId)).thenReturn(Optional.ofNullable(cartEntity));
//		when(objectMapper.convertValue(cartEntity, Cart.class)).thenReturn(cart);
//
//		Cart response = cartItemService.updateItem(cartId, 1, cartItem);
//		assertEquals(cart, response);
//	}
	
}
