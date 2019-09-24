package com.buildit.tdd.workshop.exception;

public class ShippingNotAvailableException extends Exception{
	
	public ShippingNotAvailableException() {
		super();
	}
	
	public ShippingNotAvailableException(String message) {
		super(message);
	}

}
