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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 
	 * @param customerId
	 * @return created cart
	 * 
	 */
	public Cart createItem(long cartId, CartItem cartItem) throws CartItemNotCreatedException {
		CartItemEntity cartItemEntity = objectMapper.convertValue(cartItem, CartItemEntity.class);
		cartItemEntity.setCartId(cartId);
		try {
			cartItemRepository.save(cartItemEntity);
			return findCart(cartId);
		} catch (RuntimeException e) {	
			throw new CartItemNotCreatedException();
		}
	}
	
	/**
	 * Updates product qty
	 * 
	 * @param cartId
	 * @param cartItem
	 * @return
	 */
	public Cart updateItem(long cartId, long cartItemId, CartItem cartItem) throws CartItemNotFoundException {
		Optional<CartItemEntity> opt = cartItemRepository.findById(cartItemId);
		CartItemEntity cartItemEntity = opt.get();
		cartItemEntity.setProductQty(cartItem.getProductQty());
		try {
			cartItemRepository.save(cartItemEntity);
			return findCart(cartId);
		} catch (RuntimeException e) {
			throw new CartItemNotFoundException();
		}
	}
	
	/**
	 * This method handles deletion of cart
	 * 
	 * @param cartId
	 * 
	 */
	public Cart deleteCartItem(long cartId, long cartItemId) throws CartItemNotFoundException {
		cartItemRepository.deleteById(cartItemId);
		try {
			return findCart(cartId);
		} catch (RuntimeException e) {
			throw new CartItemNotFoundException();
		}		
	}
	
	private Cart findCart(long id) {
		Optional<CartEntity> opt = cartRepository.findById(id);
		CartEntity responseCartEntity = opt.get();
		Cart response = objectMapper.convertValue(responseCartEntity, Cart.class);
		return response;
	}

}
