package com.buildit.tdd.workshop.service;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.entity.CartEntity;
import com.buildit.tdd.workshop.exception.CartNotCreatedException;
import com.buildit.tdd.workshop.exception.CartNotFoundException;
import com.buildit.tdd.workshop.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * This method return cart associated with customer
	 * 
	 * @param customerId
	 * @return Cart
	 * @throws CartNotFoundException
	 */
	public Cart getCart(long customerId) throws CartNotFoundException {
		Optional<CartEntity> cartEntity = cartRepository.findByCustomerId(customerId);
		if(!cartEntity.isPresent()) {
			throw new CartNotFoundException();
		}
		return objectMapper.convertValue(cartEntity.get(), Cart.class);
	}
	
	/**
	 * 
	 * This method handle creation of cart
	 * 
	 * @param customerId
	 * @return created cart
	 * @throws CartNotCreatedException
	 */
	public Cart createCart(long customerId) throws CartNotCreatedException {
		// TODO: Check whether user has any cart, delete or update that similar to swiggy cart
		CartEntity cartEntity = new CartEntity();
		cartEntity.setCustomerId(customerId);
		try {
			CartEntity responseCartEntity = cartRepository.save(cartEntity);
			Cart response = objectMapper.convertValue(responseCartEntity, Cart.class);
			return response;
		} catch (RuntimeException e) {	
			throw new CartNotCreatedException();
		}
	}
	
	/**
	 * This method handles deletion of cart
	 * 
	 * @param cartId
	 * 
	 */
	public void deleteCart(long cartId) {
		cartRepository.deleteById(cartId);
	}

}
