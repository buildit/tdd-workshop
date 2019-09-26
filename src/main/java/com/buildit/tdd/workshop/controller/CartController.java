package com.buildit.tdd.workshop.controller;

import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.exception.CartNotCreatedException;
import com.buildit.tdd.workshop.exception.CartNotFoundException;
import com.buildit.tdd.workshop.service.CartService;
import com.buildit.tdd.workshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
	
	@Autowired
	CartService cartService;

	/**
	 * This end point creates new cart
	 * 
	 * Usage: POST /cart
	 * 
	 * @return Empty cart
	 */
	@PostMapping
	public ResponseEntity<Cart> createCart(@RequestParam long customerId) {
		try {
			return ResponseEntity.ok(cartService.createCart(customerId));
		} catch (CartNotCreatedException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	/**
	 * This end point is used to get cart
	 * 
	 * Usage: GET /cart
	 * 
	 * @return Cart associated with customerId
	 */
	@GetMapping
	public ResponseEntity<Cart> getCart(@RequestParam long customerId) {
		try {
			return ResponseEntity.ok(cartService.getCart(customerId));
		} catch (CartNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * This end point is used for deleting cart
	 * 
	 * Usage: DELETE /cart/{cartId}
	 * 
	 * @param cartId Id of the cart
	 * @return Response
	 */
	@DeleteMapping(value = "/{cartId}")
	public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
		cartService.deleteCart(cartId);
		return ResponseEntity.ok().build();
	}

	private long getCustomer() {
		return 1;
	}
}