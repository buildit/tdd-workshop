package com.buildit.tdd.workshop.service;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.entity.CartEntity;
import com.buildit.tdd.workshop.exception.CartNotCreatedException;
import com.buildit.tdd.workshop.exception.CartNotFoundException;
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
public class CartServiceTest {

	@InjectMocks
	private CartService cartService;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private CartRepository cartRepository;
	
	private CartEntity cartEntity;
	private Cart cart;

	@Before
	public void setup() {
		cartEntity = cartEntity.builder().cartId(1).customerId(1).build();
		cart = cart.builder().cartId(1).customerId(1).build();
	}
	
	@Test
	public void createCart_shouldCreate_successfully() throws CartNotCreatedException {		
		when(cartRepository.save(Mockito.any(CartEntity.class))).thenReturn(cartEntity);
		when(objectMapper.convertValue(cartEntity, Cart.class)).thenReturn(cart);
		Cart response = cartService.createCart(1);
		assertEquals(cart, response);
	}
	
	@Test
	public void getCart_shouldReturn_Cart() throws CartNotFoundException {
		Optional<CartEntity> opt = Optional.ofNullable(cartEntity);
		when(cartRepository.findByCustomerId(Mockito.anyLong())).thenReturn(opt);
		when(objectMapper.convertValue(cartEntity, Cart.class)).thenReturn(cart);
		Cart response = cartService.getCart(1);
		assertEquals(cart, response);
	}
	
	@Test
	public void deleteCart_shouldDelete_Cart() {
		long cartId = 1;
		Mockito.doNothing().when(cartRepository).deleteById(cartId);
		cartService.deleteCart(cartId);
		Mockito.verify(cartRepository).deleteById(cartId);
	}
	
//	@Test(expected = CartNotCreatedException.class)
//	public void createCart_shouldThrow_Exception() throws CartNotCreatedException {		
//		when(cartRepository.save(Mockito.any(CartEntity.class))).thenThrow(new CartNotCreatedException());
//		Mockito.verify(cartService.createCart(1));
//	}
//	
//	@Test(expected = CartNotFoundException.class)
//	public void getCart_shouldThrow_Exception() throws CartNotFoundException {
//		Optional<CartEntity> opt = Optional.ofNullable(cartEntity);
//		when(cartRepository.findByCustomer(Mockito.anyLong())).thenReturn(opt);
//		Cart response = cartService.getCart(1);
//	}
}
