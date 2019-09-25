package com.buildit.tdd.workshop.controller;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.domain.CartItem;
import com.buildit.tdd.workshop.exception.CartItemNotCreatedException;
import com.buildit.tdd.workshop.exception.CartItemNotFoundException;
import com.buildit.tdd.workshop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cartitem")
public class CartItemController {
	
	@Autowired
	CartItemService cartItemService;

	/**
	 * This end point creates new cart
	 * 
	 * Usage: POST /items/{cartId}
	 * 
	 * @param 
	 * @return Cart created cart
	 */
	@PostMapping(value = "/{cartId}")
	public ResponseEntity<Cart> createItem(@RequestBody CartItem cartItem, @PathVariable Long cartId) {
		try {
			return ResponseEntity.ok(cartItemService.createItem(cartId, cartItem));
		} catch (CartItemNotCreatedException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	/**
	 * This end point is used to update cart item
	 * 
	 * Usage: GET /items/{cartId}/{itemId}
	 * 
	 * @return Cart associated with customerId
	 * @return cart
	 */
	@PutMapping(value = "/{cartId}/{id}")
	public ResponseEntity<Cart> updateItem(@PathVariable Long cartId, @PathVariable Long id, @RequestBody CartItem cartItem) {
		try {
			return ResponseEntity.ok(cartItemService.updateItem(cartId, id, cartItem));
		} catch (CartItemNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * This end point is used for deleting cartitem from cart
	 * 
	 * Usage: DELETE /cart/{cartId}
	 * 
	 * @param cartId Id of the cart
	 * @param id cart item id
	 * @return Response
	 * @throws CartItemNotFoundException 
	 */
	@DeleteMapping(value = "/{cartId}/{id}")
	public ResponseEntity<Cart> deleteItem(@PathVariable Long cartId, @PathVariable Long id) {
		try {
			return ResponseEntity.ok(cartItemService.deleteCartItem(cartId, id));
		} catch (CartItemNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
}
