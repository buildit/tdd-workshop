package com.buildit.tdd.workshop.exception;

public class CartItemNotFoundException extends Exception {

	
	public CartItemNotFoundException() {
	}
	
	public CartItemNotFoundException(String message) {
		super(message);
	}

}
