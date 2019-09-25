package com.buildit.tdd.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buildit.tdd.workshop.domain.ShippingAvailability;
import com.buildit.tdd.workshop.domain.ShippingDetail;
import com.buildit.tdd.workshop.exception.ShippingNotAvailableException;
import com.buildit.tdd.workshop.exception.ShippingServiceNotAvailableException;
import com.buildit.tdd.workshop.service.ShippingService;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {
	
	@Autowired
	private ShippingService shippingService;
	
	private ShippingAvailability shippingAvailability;
	private ShippingDetail shippingDetail;

	@GetMapping(value= "/{pin}")
	public ResponseEntity getShippingAvailability(@PathVariable("pin") int pincode) 
			throws ShippingNotAvailableException, ShippingServiceNotAvailableException {
		try {
			
			shippingDetail = shippingService.getShippingCostByPincode(pincode);
			shippingAvailability = ShippingAvailability.builder().available(true).build();
			return ResponseEntity.ok(shippingAvailability);
			
		} catch(ShippingNotAvailableException snae) {
			return ResponseEntity.notFound().build();
		} catch(ShippingServiceNotAvailableException ssnae) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value= "/check/{pin}")
	public ResponseEntity getShippingCost(@PathVariable("pin") int pincode) 
			throws ShippingNotAvailableException, ShippingServiceNotAvailableException {
		try {
			
			shippingDetail = shippingService.getShippingCostByPincode(pincode);
			return ResponseEntity.ok(shippingDetail);
			
		} catch(ShippingNotAvailableException snae) {
			return ResponseEntity.notFound().build();
		} catch(ShippingServiceNotAvailableException ssnae) {
			return ResponseEntity.notFound().build();
		}
	}
	
}