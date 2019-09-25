package com.buildit.tdd.workshop.controller;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.domain.CartItem;
import com.buildit.tdd.workshop.exception.CartItemNotCreatedException;
import com.buildit.tdd.workshop.exception.CartItemNotFoundException;
import com.buildit.tdd.workshop.service.CartItemService;
import com.buildit.tdd.workshop.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemControllerTest {
	
	@InjectMocks
	private CartItemController cartItemController;
	
	@Mock
	private CartItemService cartItemService;
	
	private Cart cart;
	private CartItem cartItem;
	
	private final long customerId = 1;
	private long cartId;

	@Before
	public void setup() {
		cart = cart.builder().cartId(1).customerId(1).build();
		cartItem = cartItem.builder().cartId(cart.getCartId()).productId(1).productQty((byte) 10).build();
		cartId = cart.getCartId();
	}
	
	@Test
	public void createItem_shouldCreate_successfully() throws CartItemNotCreatedException {
		when(cartItemService.createItem(cartId, cartItem)).thenReturn(cart);
		ResponseEntity<Cart> response = cartItemController.createItem(cartItem, cartId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cart, response.getBody());
	}
	
	@Test
	public void createItem_shouldReturn_badRequest() throws CartItemNotCreatedException {
		when(cartItemService.createItem(cartId, cartItem)).thenThrow(new CartItemNotCreatedException());
		ResponseEntity<Cart> response = cartItemController.createItem(cartItem, cartId);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void updateItem_shouldUpdate_successfully() throws CartItemNotFoundException {
		when(cartItemService.updateItem(cartId, 1, cartItem)).thenReturn(cart);
		ResponseEntity<Cart> response = cartItemController.updateItem(cartId, (long) 1, cartItem);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cart, response.getBody());
	}
	
	@Test
	public void updateItem_shouldReturn_notFound() throws CartItemNotFoundException {
		when(cartItemService.updateItem(cartId, 1, cartItem)).thenThrow(new CartItemNotFoundException());
		ResponseEntity<Cart> response = cartItemController.updateItem(cartId, (long) 1, cartItem);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void deleteItem_shouldReturn_badRequest() throws CartItemNotFoundException {
		when(cartItemService.deleteCartItem(cartId, (long) 1)).thenReturn(cart);
		ResponseEntity<Cart> response = cartItemController.deleteItem(cartId, (long) 1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
}
