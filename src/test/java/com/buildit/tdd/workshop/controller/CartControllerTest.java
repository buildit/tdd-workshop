package com.buildit.tdd.workshop.controller;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.domain.CartItem;
import com.buildit.tdd.workshop.exception.CartNotCreatedException;
import com.buildit.tdd.workshop.exception.CartNotFoundException;
import com.buildit.tdd.workshop.service.CartService;
import com.buildit.tdd.workshop.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
	
	@InjectMocks
	private CartController cartController;
	
	@Mock
	private CartService cartService;
	
	@Mock
	private CustomerService customerService;
	
	private Cart cart;
	private CartItem cartItem;
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
	private final long customerId = 1;
	private long cartId;

	@Before
	public void setup() {
		cart = cart.builder().cartId(1).customerId(1).build();
		cartItem = cartItem.builder().cartId(cart.getCartId()).productId(1).productQty((byte) 10).build();
		cartItems.add(cartItem);
		cartId = cart.getCartId();
	}
	
	@Test
	public void createCart_shouldCreate_successfully() throws CartNotCreatedException {
		when(cartService.createCart(customerId)).thenReturn(cart);
		when(customerService.getCustomer()).thenReturn(customerId);
		ResponseEntity<Cart> response = cartController.createCart();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cart, response.getBody());
	}
	
	@Test
	public void createCart_shouldReturn_badRequest() throws CartNotCreatedException {
		when(customerService.getCustomer()).thenReturn((long) 1);
		when(cartService.createCart(customerId)).thenThrow(new CartNotCreatedException());
		ResponseEntity<Cart> response = cartController.createCart();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void getCart_shouldReturn_customerCart() throws CartNotFoundException {
		when(customerService.getCustomer()).thenReturn(customerId);
		when(cartService.getCart(customerId)).thenReturn(cart);
		ResponseEntity<Cart> response = cartController.getCart();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cart, response.getBody());
	}
	
	@Test
	public void getCart_shouldReturn_badRequest() throws CartNotFoundException {
		when(customerService.getCustomer()).thenReturn(customerId);
		when(cartService.getCart(customerId)).thenThrow(new CartNotFoundException());
		ResponseEntity<Cart> response = cartController.getCart();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void deleteCart_shouldDelete_customerCart() {
		Mockito.doNothing().when(cartService).deleteCart(cartId);
		ResponseEntity<?> response = cartController.deleteCart(cartId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
